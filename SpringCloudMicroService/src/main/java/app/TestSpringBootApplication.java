package app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import server.SpringCloudContextConfiguration;


@SpringBootApplication
@ComponentScans({
        @ComponentScan(basePackages = "server"),
        @ComponentScan(basePackages = "service")
})
public class TestSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplication.class, args);

    }


}
