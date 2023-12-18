package server;

import entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ApplicationContextConfiguration {

    @Bean
    @Scope("singleton")
    public SessionFactory connector(){
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration().configure();
        config.addAnnotatedClass(eProducts.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        return config.buildSessionFactory(builder.build());
    }

}
