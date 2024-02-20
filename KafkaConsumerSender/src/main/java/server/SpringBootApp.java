package server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import service.CustomKafkaListener;
import service.WrapperConsumerThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootApplication
@ComponentScans({
        @ComponentScan(basePackages = "service")
})
public class SpringBootApp {

    static class Test{
        volatile int  count = 0;
    }

    static Test obj = new Test();
    private static final CountDownLatch count = new CountDownLatch(3);
    private static volatile int prevIdThread = 0;

   static public class TestThread implements Runnable{

        Logger logger = LoggerFactory.getLogger(TestThread.class);

        int idThread;

        public TestThread(){
            idThread = (int) (count.getCount());
        }

        @Override
        public void run() {


            try {

                count.await();

                synchronized (obj) {
            while(obj.count!=1000){

                if(idThread==prevIdThread+1 || (idThread == 1 && prevIdThread==3)) {

                        logger.info(Integer.toString(++obj.count));
                        prevIdThread = idThread;
                        obj.notifyAll();
                    } else obj.wait();
                }

            }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApp.class, args);
        WrapperConsumerThread wrapper = context.getBean(WrapperConsumerThread.class);
       /*
        new Thread(new TestThread()).start();
        count.countDown();
        new Thread(new TestThread()).start();
        count.countDown();
        new Thread(new TestThread()).start();
        count.countDown();
        */

        wrapper.run();
        wrapper.run();

    }


}



