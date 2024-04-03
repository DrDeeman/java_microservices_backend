package App;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScans({
        @ComponentScan(basePackages = "controller"),
        @ComponentScan(basePackages = "service"),
        @ComponentScan(basePackages = "server"),
        @ComponentScan(basePackages = "dao")
})
@EnableR2dbcRepositories(basePackages = "repository")
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
