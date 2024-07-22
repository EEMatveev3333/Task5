package org.example;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestBeans {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer(DynamicPropertyRegistry registry) {
        //return new PostgreSQLContainer<>("postgres:15");
        //return new PostgreSQLContainer<>("postgres:latest")
        //return new PostgreSQLContainer<>("service-db-1")
        return new PostgreSQLContainer<>("postgres")
                    .withDatabaseName("postgres")
                    .withUsername("admin")
                    .withPassword("root");
    }

//    final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(dockerImage)
//            .withDatabaseName("jooq_airbyte_configs")
//            .withUsername("jooq_generator")
//            .withPassword("jooq_generator");
//    container.start();

}
