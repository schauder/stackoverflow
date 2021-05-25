package com.example.auditing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.relational.core.mapping.event.RelationalAuditingCallback;

import java.time.Instant;

@SpringBootApplication
public class AuditingWithTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditingWithTemplateApplication.class, args);
	}


	@Bean
	RelationalAuditingCallback isNewAwareAuditingHandler(JdbcMappingContext context) {

		return new RelationalAuditingCallback(new IsNewAwareAuditingHandler(PersistentEntities.of(context)) {
			@Override
			public Object markAudited(Object source) {

				if (!(source instanceof Product)) {
					return source;
				}

				Product product = (Product) source;
				if (product.createdDate == null) {
					product.createdDate = Instant.now();
				}

				return source;
			}
		});
	}
}
