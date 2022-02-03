package com.example.createdatwithrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class CreatedAtWithRecordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreatedAtWithRecordsApplication.class, args);
	}

}
