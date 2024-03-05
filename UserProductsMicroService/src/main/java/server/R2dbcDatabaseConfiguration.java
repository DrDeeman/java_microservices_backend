package server;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@EnableR2dbcAuditing
public class R2dbcDatabaseConfiguration extends AbstractR2dbcConfiguration
{


    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.parse(
                                "r2dbc:postgresql://127.0.0.1:5432/users_products"
                        )
                        .mutate()
                        .option(ConnectionFactoryOptions.USER,"postgres")
                        .option(ConnectionFactoryOptions.PASSWORD,"postgres")
                        .build()
        );
    }

    @Bean(name="r2dbcDatabaseClient")
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .namedParameters(true)
                .build();
    }


}
