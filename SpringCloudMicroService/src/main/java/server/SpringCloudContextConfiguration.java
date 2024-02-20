package server;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.client.WebClient;
import service.AuthenticationPrefiler;

import java.util.function.Supplier;


@Configuration
public class SpringCloudContextConfiguration {

    @Value(value="${urls.products_address}")
    String products_address;

    @Value(value="${urls.products_path}")
    String products_path;

    @Value(value="${urls.auth_address}")
    String auth_address;

    @Value(value="${urls.auth_path}")
    String auth_path;

    @Value(value="${urls.users_address}")
    String users_address;

    @Value(value="${urls.users_path}")
    String users_path;


    @Bean
    @Primary
    public PathRoutePredicateFactory cr(){
        return new PathRoutePredicateFactory();
    }
    @Bean
    @Primary
    public RouteLocatorBuilder CrouteLocatorBuilder(ConfigurableApplicationContext context) {
        return new RouteLocatorBuilder(context);
    }

    @Bean
    @Primary
    public RewritePathGatewayFilterFactory CR(){
        return new RewritePathGatewayFilterFactory();
    }

    @Bean
    @Qualifier("authWebClient")
    public WebClient authWebClient(){
          return WebClient.builder()
                  .baseUrl(auth_address+auth_path+"/auth")
                  .build();
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthenticationPrefiler authFilter){
        return builder.routes()
                .route(r->r.path("/products_api/**")
                        .filters(f-> f.rewritePath(
                                "/products_api/(?<segment>.*)", products_path+"/${segment}"
                        ))
                        .uri(products_address))

                .route(r->r.path("/users_api/login")
                        .filters(f->f.rewritePath(
                                "/users_api/login",auth_path+"/login"
                        ))
                .uri(auth_address))

                .route(r->r.path("/users_api/auth")
                        .filters(f->f.rewritePath(
                                "/users_api/auth",auth_path+"/auth"
                        ))
                        .uri(auth_address))

                .route(r->r.path("/users_api/logout")
                        .filters(f->f.rewritePath(
                                "/users_api/logout",auth_path+"/logout"
                        ))
                        .uri(auth_address))

                .route(r->r.path("/users_api/refresh_token")
                        .filters(f->f.rewritePath(
                                "/users_api/refresh_token",auth_path+"/refresh_token"
                        ))
                        .uri(auth_address))

                .route(r->r.path("/users_api/**")
                        .filters(f->f.rewritePath(
                                "/users_api/(?<segment>.*)",users_path+"/${segment}"
                        ).filter(authFilter.apply(new AuthenticationPrefiler.Config()))
                        )
                        .uri(users_address))
                .build();
    }



}
