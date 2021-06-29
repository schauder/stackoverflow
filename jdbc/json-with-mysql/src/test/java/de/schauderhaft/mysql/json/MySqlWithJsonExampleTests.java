package de.schauderhaft.mysql.json;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;

@DataJdbcTest
class MySqlWithJsonExampleTests {

	@Autowired
	JdbcAggregateTemplate template;

	@Test
	void test() {

		Product product = new Product();
		product.name = "Ice Cream";
		product.value = "{\"key1\": \"value1\", \"key2\": \"value2\"}";

		Product afterInsert = template.insert(product);


	}
}
