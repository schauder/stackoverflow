package com.example.jpa2189nestedprojectionnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.jdbc.datasource.init.ScriptUtils.EOF_STATEMENT_SEPARATOR;

import java.net.URI;
import java.util.UUID;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql(scripts = {"classpath:scripts/schema.sql", "classpath:scripts/data.sql"}, //
        config = @SqlConfig( //
                separator = EOF_STATEMENT_SEPARATOR))
class Jpa2189NestedProjectionNullApplicationTests {

	@Container
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:12-alpine");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Autowired
	ProductDetailsRepository detailsRepository;

	@Test
	void fetchProjectedProductWithDetailsTest() {

		var storeFrontProduct = detailsRepository.findById(
				UUID.fromString("1fb9e691-033d-4092-b326-99088d401ec9"), StoreAdminProduct.class);

		assertThat(storeFrontProduct).isPresent()
				.get(InstanceOfAssertFactories.type(StoreAdminProduct.class))
				.hasFieldOrPropertyWithValue("costPrice", 350.0)
				.extracting(StoreAdminProduct::getProduct).isNotNull() // Fails here
				.hasFieldOrPropertyWithValue("featuredMedia", URI.create("/files/products/jordans.jpeg"));
	}


}
