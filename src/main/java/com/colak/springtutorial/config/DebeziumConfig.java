package com.colak.springtutorial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConfig {

    // Injecting values from application.yaml using @Value annotation
    @Value("${debezium.connector.name}")
    private String connectorName;

    @Value("${debezium.connector.class}")
    private String connectorClass;

    @Value("${debezium.connector.tasks.max}")
    private String tasksMax;

    @Value("${debezium.connector.database.server.name}")
    private String databaseServerName;

    @Value("${debezium.connector.database.hostname}")
    private String databaseHostname;

    @Value("${debezium.connector.database.port}")
    private String databasePort;

    @Value("${debezium.connector.database.user}")
    private String databaseUser;

    @Value("${debezium.connector.database.password}")
    private String databasePassword;

    @Value("${debezium.connector.database.names}")
    private String databaseNames;

    @Value("${debezium.connector.topic.prefix}")
    private String topicPrefix;

    @Value("${debezium.connector.database.history.kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${debezium.connector.database.history.kafka.topic}")
    private String kafkaHistoryTopic;

    @Bean
    public io.debezium.config.Configuration debeziumConfiguration() {
        // Create the Debezium engine configuration
        return io.debezium.config.Configuration.create()
                .with("name", connectorName)
                .with("connector.class", connectorClass)
                .with("tasks.max", tasksMax)
                .with("database.server.name", databaseServerName)
                .with("database.hostname", databaseHostname)
                .with("database.port", databasePort)
                .with("database.user", databaseUser)
                .with("database.password", databasePassword)
                .with("database.names", databaseNames)
                .with("database.encrypt", false)
                .with("database.trustServerCertificate", true)
                .with("topic.prefix", topicPrefix)
                .with("database.history.kafka.bootstrap.servers", kafkaBootstrapServers)
                .with("database.history.kafka.topic", kafkaHistoryTopic)
                .build();

    }
}
