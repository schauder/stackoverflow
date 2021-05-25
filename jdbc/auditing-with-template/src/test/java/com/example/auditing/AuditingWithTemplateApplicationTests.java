package com.example.auditing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

@DataJdbcTest
class AuditingWithTemplateApplicationTests {

	@Autowired
	JdbcAggregateTemplate template;

	@Test
	void test() {

		Product product = new Product();
		product.id = 27L;
		product.value = "Ice Creame";

		Product afterInsert = template.insert(product);

		assertThat(afterInsert.createdDate).isAfter(Instant.now().minusSeconds(1));
	}
}
