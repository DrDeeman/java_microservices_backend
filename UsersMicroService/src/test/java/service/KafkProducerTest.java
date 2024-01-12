package service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import server.TestSpringBootApplication;

@SpringBootTest(classes = TestSpringBootApplication.class)
public class KafkProducerTest {

  @Mock
  private KafkaTemplate<String,String> kt;

  @Test
    public void sendMessage_test(){}
}
