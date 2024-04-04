package controller;

import App.TestSpringBootApplication;
import DAO.DAOUsers;
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
@WebMvcTest(controllers = cUsers.class)
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




    @MockBean
    DAOUsers dUsers;







   // @Test
    public void getListTest() throws Exception {

        Map<String, List<eUsers>> groupList = this.list.stream()
                .collect(Collectors.groupingBy(u->u.getEmail()));

        Collector<eUsers, StringJoiner, String> col = Collector.of(
                ()->new StringJoiner(",")  ,
                (j,p)->j.add(p.getName()),
                (j1, j2)->j1.merge(j2),
                StringJoiner::toString
                );

        Mockito.when(this.dUsers.getUserByGroupEmail()).thenReturn(
              groupList.keySet().stream().map(k->new TestCustomRecord(
                                groupList.get(k).stream().collect(col),k
                      )).toList());

        this.mock.perform(get("/test/getUserByEmailGroup"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("stalkerdrdeeman@gmail.com"));
    }

    //@Test
    public void createUsers_test()throws Exception{

        eUsers user = this.list.get(0);

         ObjectNode tuser = this.mapper.createObjectNode();
         tuser.put("login",user.getLogin());
         tuser.put("password",user.getPassword());
         tuser.put("email",user.getEmail());


        Mockito.doNothing().when(this.dUsers).addUser(user);

        MockHttpServletRequestBuilder mockReq = post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tuser));

        MvcResult result = this.mock.perform(mockReq)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

       assertEquals(result.getResponse().getHeader("Content-type"),"text/plain");
       assertEquals(result.getResponse().getContentAsString(),"User created");

    }


}
