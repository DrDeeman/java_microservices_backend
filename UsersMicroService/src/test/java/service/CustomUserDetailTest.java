package service;

import entity.eUsers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import server.TestSpringBootApplication;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestSpringBootApplication.class)
public class CustomUserDetailTest {

    @Test
    public void loadUserByUsername_test() throws Exception{

       //CustomUserDetail cud =  Mockito.mock(CustomUserDetail.class);

       //eUsers user = (eUsers)cud.loadUserByUsername("demo");

       assertNotNull(null);

    }
}
