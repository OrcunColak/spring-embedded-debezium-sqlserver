package com.colak.springtutorial.embeddeddebezium;

import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class DebeziumListener {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;

    @PostConstruct
    public void startDebezium(Configuration debeziumConfiguration) {
        Properties properties = debeziumConfiguration.asProperties();

        debeziumEngine = DebeziumEngine
                .create(Json.class)
                .using(properties)
                .notifying(record -> log.info("Received change event: {}", record))
                .build();

        executor.submit(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            // stop the engine safely and gracefully
            // The engine’s connector will stop reading information from the source system, forward all remaining change events to your handler function,
            // and flush the latest offets to offset storage. Only after all of this completes will the engine’s run() method return.
            this.debeziumEngine.close();
        }
        executor.close();
    }
}

