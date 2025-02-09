package com.colak.springtutorial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConfig {

    @Value("${debezium.name}")
    private String name;

    @Value("${debezium.connector.class}")
    private String connectorClass;

    @Value("${debezium.database.hostname}")
    private String databaseHostname;

    @Value("${debezium.database.port}")
    private String databasePort;

    @Value("${debezium.database.user}")
    private String databaseUser;

    @Value("${debezium.database.password}")
    private String databasePassword;

    @Value("${debezium.database.dbname}")
    private String databaseDbname;

    @Value("${debezium.database.server_name}")
    private String databaseServerName;

    @Value("${debezium.database.snapshot_mode}")
    private String snapshotMode;

    @Value("${debezium.include.schema_changes}")
    private boolean includeSchemaChanges;

    @Value("${debezium.include.tables}")
    private String tableIncludeList;

    @Bean
    public io.debezium.config.Configuration debeziumConfiguration() {
        return io.debezium.config.Configuration.create()
                .with("name", name)
                .with("connector.class", connectorClass)
                .with("database.hostname", databaseHostname)
                .with("database.port", databasePort)
                .with("database.user", databaseUser)
                .with("database.password", databasePassword)
                .with("database.dbname", databaseDbname)
                .with("database.server.name", databaseServerName)
                .with("snapshot.mode", snapshotMode)
                .with("include.schema.changes", String.valueOf(includeSchemaChanges))
                .with("table.include.list", tableIncludeList)
                .with("database.history", "io.debezium.relational.history.MemoryDatabaseHistory") // For in-memory history

                .build();

    }
}
