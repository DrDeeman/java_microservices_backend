package server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
          registry.addResourceHandler("/scripts/*.js")
                  .addResourceLocations("/scripts/");
          registry.addResourceHandler("/remoteUsers.js")
                  .addResourceLocations("/remoteUsers.js");
          registry.addResourceHandler("/assets/**")
                  .addResourceLocations("/WEB-INF/assets/");
          registry.addResourceHandler("/*.ico")
                  .addResourceLocations("/assets/images/");
          registry.addResourceHandler("/index.html")
                  .addResourceLocations("/view/index.html");
    }
}
