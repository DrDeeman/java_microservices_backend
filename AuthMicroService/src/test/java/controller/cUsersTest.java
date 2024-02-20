package controller;

import App.TestSpringBootApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.eUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import records.TestCustomRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@ContextConfiguration(classes = {TestSpringBootApplication.class})
@WebMvcTest(controllers = auth.class)
public class cUsersTest {

   @Autowired
   private WebApplicationContext wac;

   @Autowired
   ObjectMapper mapper;

   private MockMvc mock;

   private List<eUsers> list;

   @BeforeEach
   public void setup(){
       this.mock =  webAppContextSetup(this.wac).build();
       list = Arrays.asList(
               new eUsers("test2","test2","test22323","stalkerdrdeeman@gmail.com"),
               new eUsers("test2","test2","test222323","stalkerdrdeeman@gmail.com")
       );
   }




    
   // @Test
    public void getUsers_test()throws Exception{

        eUsers user = new eUsers("demo","demo1","demo1","ss@gmail.com");
        //Mockito.when(this.dUsers.getUser("demo","demo1")).thenReturn(user);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext sc = Mockito.mock(SecurityContext.class);
        //AuthenticationManager mn = Mockito.mock(AuthenticationManager.class);

        Mockito.when(sc.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(sc);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        this.mock.perform(get("/users_api/auth").contextPath("/users_api"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("demo1"));


    }




}
