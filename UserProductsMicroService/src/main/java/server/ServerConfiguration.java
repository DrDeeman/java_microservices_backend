package server;

import entity.eUsers;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServerConfiguration {


    @Autowired
    SessionFactory factory;


    @Bean
    WebMvcConfigurer configurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                WebMvcConfigurer.super.addFormatters(registry);
                registry.addConverter(String.class, eUsers.class, id->factory.getCurrentSession().find(eUsers.class,id));
            }
        };
    }
}
