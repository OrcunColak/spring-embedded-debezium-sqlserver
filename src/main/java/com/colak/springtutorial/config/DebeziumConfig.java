package com.colak.springtutorial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConfig {

    // Injecting values from application.yaml using @Value annotation

    @Value("${spring.datasource.hostname}")
    private String databaseHostName;

    @Value("${spring.datasource.port}")
    private String databasePort;

    @Value("${spring.datasource.username}")
    private String databaseUser;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.datasource.encrypt}")
    private String databaseEncrypt;

    @Value("${spring.datasource.trust-server-certificate}")
    private String databaseTrustServerCertificate;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUser;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    /********************************************************************************************/
    @Value("${debezium.database.names}")
    private String databaseNames;

    @Value("${debezium.database.table.include-list}")
    private String databaseTableIncludeList;

    @Value("${debezium.topic-prefix}")
    private String topicPrefix;

    @Value("${debezium.offset.storage.jdbc.offset-table-name}")
    private String offsetTableName;

    @Value("${debezium.offset.storage.jdbc.offset-table-ddl}")
    private String offsetTableDdl;

    @Value("${debezium.offset.storage.jdbc.schema-history-table-name}")
    private String schemaHistoryTableName;

    @Value("${debezium.offset.storage.jdbc.schema-history-table-ddl}")
    private String schemaHistoryTableDdl;

    @Value("${debezium.offset.storage.jdbc.schema-history-table-exists}")
    private String schemaHistoryTableExists;

    @Bean
    public io.debezium.config.Configuration debeziumConfiguration() {
        // Create the Debezium engine configuration
        return io.debezium.config.Configuration.create()
                .with("name", "embedded-engine")
                .with("connector.class", "io.debezium.connector.sqlserver.SqlServerConnector")
                .with("snapshot.mode", "no_data")
                .with("tasks.max", 1)
                .with("database.hostname", databaseHostName)
                .with("database.port", databasePort)
                .with("database.user", databaseUser)
                .with("database.password", databasePassword)
                .with("database.encrypt", databaseEncrypt)
                .with("database.trustServerCertificate", databaseTrustServerCertificate)

                .with("table.include.list", databaseTableIncludeList)

                .with("topic.prefix", topicPrefix)

                .with("include.schema.changes", false)

                .with("offset.storage", "io.debezium.storage.jdbc.offset.JdbcOffsetBackingStore")
                .with("offset.storage.jdbc.url", jdbcUrl)
                .with("offset.storage.jdbc.user", jdbcUser)
                .with("offset.storage.jdbc.password", jdbcPassword)
                .with("offset.storage.jdbc.offset.table.name", offsetTableName)
                .with("offset.storage.jdbc.offset.table.ddl", offsetTableDdl)
                .with("offset.flush.interval.ms", 10000)

                .with("schema.history.internal", "io.debezium.storage.jdbc.history.JdbcSchemaHistory")
                .with("schema.history.internal.jdbc.url", jdbcUrl)
                .with("schema.history.internal.jdbc.user", jdbcUser)
                .with("schema.history.internal.jdbc.password", jdbcPassword)
                .with("schema.history.internal.jdbc.schema.history.table.name", schemaHistoryTableName)
                .with("schema.history.internal.jdbc.schema.history.table.ddl", schemaHistoryTableDdl)
                .with("schema.history.internal.jdbc.schema.history.table.exists", schemaHistoryTableExists)
                .with("schema.history.internal.store.only.captured.tables.ddl", true)

                .build();

    }
}
