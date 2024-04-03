package controller;

import App.TestSpringBootApplication;
import DAO.DAOUserProducts;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.eProducts;
import entity.eUsers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ContextConfiguration(classes = {TestSpringBootApplication.class})
@WebMvcTest(controllers = CRUDUserProducts.class)
public class TestCRUDUserProducts {

   @Autowired
   MockMvc mock;

   @MockBean
    DAOUserProducts dao;

   @Autowired
   ObjectMapper mapper;

   static List<eUsers> users;

   @BeforeAll
    public static void setup(){
       users = new ArrayList<>();
       eUsers u1 = new eUsers("test1","test1","test1","test@gmail.com");
       u1.setId(363);
       u1.addProduct(new eProducts("product1",new BigDecimal(500.00),LocalDateTime.parse("2023-01-01T00:00:00"),4.9,"path"));
       u1.addProduct(new eProducts("product2",new BigDecimal(400.00),LocalDateTime.parse("2022-01-01T00:00:00"),4.9,"path"));
       users.add(u1);
   }



/*
   @TestConfiguration
   static class ConfigTest{
            @Bean
           WebMvcConfigurer configurer(){
               return new WebMvcConfigurer() {
                  @Override
                  public void addFormatters(FormatterRegistry registry) {
                     WebMvcConfigurer.super.addFormatters(registry);
                     registry.addConverter(String.class, eUsers.class, id->users.get(0));
                  }
               };
            }
   }
*/
   //@Test
    public void testGetUserWithProducts() throws Exception {
       eUsers user = users.get(0);
       eProducts product = new eProducts("product3",new BigDecimal(100.00),LocalDateTime.parse("2020-01-01T00:00:00"),2.9,"path");
       Mockito.doNothing().when(this.dao).addedProductForUser(user,product);
       user.addProduct(product);

       this.mock.perform(post("/user/363/products")
                           .contentType(MediaType.APPLICATION_JSON)
                           .accept(MediaType.APPLICATION_JSON)
                           .content(mapper.writeValueAsString(product)))
               .andDo(print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@gmail.com"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.products").isArray());
   }



}
