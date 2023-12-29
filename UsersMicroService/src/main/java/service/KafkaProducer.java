package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import server.ApplicationContextProvider;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topic){
        new KafkaProducerThread(message, topic).start();
    }

    private class KafkaProducerThread extends Thread{

        private final String message;
        private final String topic;

        public KafkaProducerThread(String message, String topic) {
            super();
            this.message = message;
            this.topic = topic;
        }

        @Override
        public void run() {

            System.out.println("start send...");
            try {
                Thread.sleep(10000);
                kafkaTemplate.send(topic, message);
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("end send...");
        }
    }


}
