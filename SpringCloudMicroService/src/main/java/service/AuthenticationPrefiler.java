package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationPrefiler extends AbstractGatewayFilterFactory<AuthenticationPrefiler.Config> {

    private final List<String> excludeRoute;


    public AuthenticationPrefiler(
            @Value(value="${urls.auth_path}") String auth_path,
            @Value(value="${urls.users_path}") String users_path
    ){
        excludeRoute = new ArrayList<>();
        excludeRoute.add(users_path+"/test/getUserByEmailGroup");
        excludeRoute.add(auth_path+"/login");
        excludeRoute.add(users_path+"/index.html");
        excludeRoute.add(users_path+"/assets/");
        excludeRoute.add(users_path+"/scripts/");
        excludeRoute.add(users_path+"/remoteUsers.js");
        excludeRoute.add(users_path+".ico");
    }


   @Autowired
   WebClient authWebClient;



    @Override
    public GatewayFilter apply(Config config){
         return (exchange, chain)->{
             ServerHttpRequest req = exchange.getRequest();
             String path = req.getURI().getPath();


             if(excludeRoute.stream().noneMatch(
                     route->path.contains(route)
             )){

               return authWebClient
                       .get()
                       .header("Authorization", req.getHeaders().getFirst("Authorization"))
                       .retrieve()
                       .bodyToMono(user.class)
                       .map(res->{
                           req.mutate().header("user_id",Integer.toString(res.getId()));
                           req.mutate().header("user_roles",res.getRoles().stream().collect(Collectors.joining(",")));
                           return exchange;
                       })
                       .flatMap(chain::filter)
                       .onErrorResume(error-> {
                           Optional<HttpStatusCode> code = Optional.empty();
                           String message = error.getMessage();

                           if(error instanceof WebClientResponseException){
                               WebClientResponseException webError = (WebClientResponseException)error;
                               code = Optional.of(webError.getStatusCode());
                               message = webError.getResponseBodyAsString();
                           }

                           ServerHttpResponse errResponse =  exchange.getResponse();

                           try {
                           ObjectMapper writer = new ObjectMapper();
                               DataBufferFactory factory = exchange.getResponse().bufferFactory();
                           errResponse.setStatusCode(code.orElse(HttpStatusCode.valueOf(500)));

                           byte[] byteData = writer.writeValueAsBytes(message);
                           return errResponse.writeWith(Mono.just(byteData).map(t->factory.wrap(t)));
                           } catch (JsonProcessingException e) {

                           }
                           return errResponse.setComplete();

                       });
             }
             return chain.filter(exchange);
         };
    }

    public static class Config{}
}
