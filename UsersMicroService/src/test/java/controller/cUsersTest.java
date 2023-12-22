package controller;

import DAO.DAOUsers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.eUsers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import server.TestSpringBootApplication;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestSpringBootApplication.class)
@AutoConfigureMockMvc
public class cUsersTest {

    @Autowired
    MockMvc mock;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DAOUsers dUsers;


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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("demo1"));
    }

    @Test
    public void createUsers_test()throws Exception{
        eUsers user = new eUsers("demo1","demo2","demo2","ss@gmail.com");

         ObjectNode tuser = this.mapper.createObjectNode();
         tuser.put("login","demo");
         tuser.put("password","pppsdfsdfsdf");
         tuser.put("email","ss@gmail.com");


        Mockito.doNothing().when(this.dUsers).addUser(user);

        MockHttpServletRequestBuilder mockReq = MockMvcRequestBuilders.post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tuser));

        MvcResult result = this.mock.perform(mockReq)
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getHeader("Content-type"),"application/text");

    }


}
