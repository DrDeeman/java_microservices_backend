package server;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import service.CustomPartitioner;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationContextConfiguration {


/*
    @Bean
    @Scope("singleton")
    public SessionFactory connector(){
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration().configure();
        config.addAnnotatedClass(eUsers.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        return config.buildSessionFactory(builder.build());
    }

*/

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    @Scope("prototype")
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                this.bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
        configProps.put(ProducerConfig.ACKS_CONFIG,"all");
        configProps.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,CustomPartitioner.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }



    @Bean
    @Scope("prototype")
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String,String>kt = new KafkaTemplate<>(producerFactory());
        return kt;
    }



    @Bean(name="entityManagerFactory")
    @Primary
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("entity");
        return sessionFactory;
    }



    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://172.16.238.10:5432/users");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }







}
