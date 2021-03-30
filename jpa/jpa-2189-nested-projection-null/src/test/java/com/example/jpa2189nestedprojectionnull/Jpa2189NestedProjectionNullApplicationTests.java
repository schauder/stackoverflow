package com.example.jpa2189nestedprojectionnull;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class Jpa2189NestedProjectionNullApplicationTests {


	@Container
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>();

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
				UUID.fromString("1fb9-e691-033d-4092-b326-99088d401ec9"), StoreAdminProduct.class);

		assertThat(storeFrontProduct).isPresent()
				.get(InstanceOfAssertFactories.type(StoreAdminProduct.class))
				.hasFieldOrPropertyWithValue("costPrice", 350.0)
				.extracting(StoreAdminProduct::getProduct).isNotNull() // Fails here
				.hasFieldOrPropertyWithValue("featuredMedia", URI.create("/files/products/jordans.jpeg"));
	}


}
