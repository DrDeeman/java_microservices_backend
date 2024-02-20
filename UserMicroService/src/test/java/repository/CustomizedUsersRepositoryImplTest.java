package repository;

import entity.eUsers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import records.TestCustomRecord;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.internal.matchers.text.ValuePrinter.print;


@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "entity")
class TestConfiguration {}


@DataJpaTest
public class CustomizedUsersRepositoryImplTest {

    @Autowired
    private CustomizedUsersCrudRepository rep;


    @BeforeEach
    public void initDb(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        List<eUsers> pool = List.of(
                new eUsers("test1","ltest1",encoder.encode("ptest1"),"stalkerdrdeeman@gmail.com"),
                new eUsers("test2","ltest2",encoder.encode("ptest2"),"stalkerdrdeeman@gmail.com"),
                new eUsers("test3","ltest3",encoder.encode("ptest3"),"stalkerdrdeeman@gmail.com")
        );

        this.rep.saveAll(pool);
    }

    @Test
    public void listEmailTest(){
        List<TestCustomRecord> list =  this.rep.getListGroupEmail();
        print(list);
        assertThat(list).isNotNull();
    }

    @Test
    public void countListUser(){
        List<eUsers> users =  this.rep.findAll();
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    public void getListTest(){
        eUsers user = this.rep.findByLogin("ltest1");
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("test1");

    }
}
