package server;


import entity.eUsers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import service.CustomUserDetail;
import service.JwtAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

         http.logout(logout->logout.logoutSuccessHandler(
                        (req,res,auth)->res.setStatus(HttpStatus.OK.value())
                ))
                .authorizeHttpRequests(
                (authorize)-> {
                    try {
                        authorize
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//disable http sessions
                                .and()
                                .authenticationManager(this.authenticationManager())
                                .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        )

                .exceptionHandling(customizer-> customizer
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                 .cors().and().csrf().disable();
        return http.build();

    }



    @Bean
    public PasswordEncoder plainPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder(){ return new BCryptPasswordEncoder();};

    /*
    @Bean UserDetailsService customService(){
        return new CustomUserDetail();
    }
    */


    @Autowired
    CustomUserDetail cud;
    @Bean
    @Scope("prototype")
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.cud);
        authProvider.setPasswordEncoder(cryptPasswordEncoder());
        //authProvider.setPasswordEncoder(plainPasswordEncoder());
        return new ProviderManager(authProvider);
    }





    @Bean
    public WebSecurityCustomizer ignoreResources(){
        return (webSecurity) -> webSecurity.ignoring().requestMatchers("/users/");
    }


}
