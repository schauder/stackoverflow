package de.schauderhaft.mysql.json;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.relational.core.mapping.event.RelationalAuditingCallback;

import java.time.Instant;

@SpringBootApplication
public class MySqlWithJsonExample {

	public static void main(String[] args) {
		SpringApplication.run(MySqlWithJsonExample.class, args);
	}

}
