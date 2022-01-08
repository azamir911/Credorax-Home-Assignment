package com.credorax.audit;

import com.credorax.service.TransactionSavedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@PropertySources({@PropertySource("classpath:application.properties")})
public class AuditService {

    @Value("${audit.file.path}")
    String fileName;

    ObjectMapper objectMapper;

    Path path;

    @PostConstruct
    protected void init() {
        objectMapper = new ObjectMapper();
        path = Paths.get(fileName);
    }

    @EventListener(TransactionSavedEvent.class)
    protected void log(TransactionSavedEvent transactionSavedEvent) {
        // Maybe we should have a lock over here
        final var transaction = transactionSavedEvent.getSource();
        try {
            log.info("Got a transaction event: '{}'", transaction);
            final var valueAsString = objectMapper.writeValueAsString(transaction) + System.lineSeparator();
            Files.write(path, valueAsString.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.warn("Got an exception while trying to audit transaction to '{}'", transaction);
        }
    }

    public void updateFile(String file) throws IOException {
        path = Paths.get(file);
        if (!path.toFile().exists()) {
            path.toFile().createNewFile();
        }
        log.info("Audit file updated to '{}'", path.toString());
    }

}
