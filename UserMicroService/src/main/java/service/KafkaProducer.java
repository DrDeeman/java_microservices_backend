package service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private ProducerFactory<String, String> factory;

    public void sendMessage(String message, String topic){
        new Thread(new KafkaProducerThread(message, topic)).start();
    }



    private class KafkaProducerThread implements Runnable{

        private final String message;
        private final String topic;



        public KafkaProducerThread(String message, String topic) {
            this.message = message;
            this.topic = topic;
        }

        @Override
        public void run() {

            final ValContainer<Boolean> success = new ValContainer<>(false);
            final ValContainer<Boolean> sender = new ValContainer<>(false);

            KafkaTemplate<String,String>kafkaTemplate = null;

            synchronized(factory) {
                kafkaTemplate = new KafkaTemplate<>(factory);
            }



            kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {


                private Logger logger = LoggerFactory.getLogger(this.getClass());

                @Override
                public void onSuccess(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata) {
                    logger.info("message success write in topic "+producerRecord.topic());
                    success.setVal(true);
                }

                @Override
                public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata, Exception exception) {
                    logger.error("message not success write in topic "+producerRecord.topic());
                    success.setVal(false);
                    sender.setVal(false);

                }
            });

               while(!success.getVal()) {

                   if(!sender.getVal()) {
                       sender.setVal(true);

                           kafkaTemplate.send(topic, message);



                   }

                   Thread.yield();
               }


            kafkaTemplate.destroy();

        }





        public class ValContainer<T> {
            private T val;

            public ValContainer() {
            }

            public ValContainer(T v) {
                this.val = v;
            }

            public T getVal() {
                return val;
            }

            public void setVal(T val) {
                this.val = val;
            }
        }
    }


}
