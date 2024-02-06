package server;


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

import java.util.function.Supplier;


@Configuration
public class SpringCloudContextConfiguration {


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
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/products_api/**")
                        .filters(f->f.rewritePath(
                                "/products_api/(?<segment>.*)","/products/${segment}"
                        ))
                        .uri("http://172.16.238.22:8080"))
                .route(r->r.path("/users_api/**")
                        .filters(f->f.rewritePath(
                                "/users_api/(?<segment>.*)","/users/${segment}"
                        ))
                        .uri("http://172.16.238.12:8080"))
                .build();
    }



}
