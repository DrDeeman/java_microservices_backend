package App;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@ComponentScans({
        @ComponentScan(basePackages = "controller"),
        @ComponentScan(basePackages = "service"),
        @ComponentScan(basePackages = "server"),
        @ComponentScan(basePackages = "DAO")
})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "entity")
public class TestSpringBootApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TestSpringBootApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplication.class, args);
    }


}
