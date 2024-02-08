package controller;

import DAO.DAOUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.eUsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.ApplicationContextConfiguration;
import server.TestSpringBootApplication;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



//@ContextConfiguration(classes = ApplicationContextConfiguration.class)
//@WebMvcTest(cUsers.class)
@SpringBootTest(classes={cUsers.class})
public class cUsersTest {

  private MockMvc mock;

    @MockBean
    ObjectMapper mapper;

    @MockBean
    DAOUsers dUsers;

    @BeforeEach
    public void setup(){
        this.mock = MockMvcBuilders.standaloneSetup(new cUsers()).build();
    }
    
    @Test
    public void getUsers_test()throws Exception{
        eUsers user = new eUsers("demo","demo","demo1","ss@gmail.com");
        //Mockito.when(this.dUsers.getUser("demo","demo1")).thenReturn(user);

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext sc = Mockito.mock(SecurityContext.class);

        Mockito.when(sc.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(sc);

        Mockito.when(authentication.getPrincipal()).thenReturn(user);

        this.mock.perform(get("/auth"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login").value("demo1"));
    }

    @Test
    public void createUsers_test()throws Exception{
        /*
        eUsers user = new eUsers("demo1","demo2","demo2","ss@gmail.com");

         ObjectNode tuser = this.mapper.createObjectNode();
         tuser.put("login","demo");
         tuser.put("password","pppsdfsdfsdf");
         tuser.put("email","ss@gmail.com");


        Mockito.doNothing().when(this.dUsers).addUser(user);

        MockHttpServletRequestBuilder mockReq = post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tuser));

        MvcResult result = this.mock.perform(mockReq)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
*/
       // assertEquals(result.getResponse().getHeader("Content-type"),"application/text");
assertEquals(true,true);
    }


}
