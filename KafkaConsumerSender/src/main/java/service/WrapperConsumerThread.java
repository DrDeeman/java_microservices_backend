package service;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;
import records.rUser;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WrapperConsumerThread {

    @Autowired
    private ConsumerFactory<String, String> factory;

    public void run(){
        new Thread(new ConsumerThread()).start();
    }
    private class ConsumerThread implements Runnable {

        private AtomicInteger all_count_threads = new AtomicInteger(0);
        private final int number;
        private final Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

        public ConsumerThread() {
            this.number = all_count_threads.incrementAndGet();
            }


        @Override
        public void run() {
            logger.info("Start consumer "+ Integer.toString(number));
            EmailSender sender = ApplicationContextProvider.getApplicationContext().getBean(EmailSender.class);

            Consumer<String, String> consumer = null;

                synchronized (factory) {
                    consumer = factory.createConsumer();
                }
                consumer.subscribe(List.of("messages"));

                while (true) {
                    try {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                    for (ConsumerRecord<String, String> record : records) {
                        rUser user = new Gson().fromJson(record.value(),rUser.class);
                        sender.sendSimpleEmail(
                                user.email(),
                               "New registration",
                                "You register,"+user.login()+" . Your password:"+user.password());
                    }

                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }

        }
    }
}
