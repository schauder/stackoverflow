package de.schauderhaft.mongoid;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class MongoIdApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	CustomerRepository customers;

	@Autowired
	MongoTemplate mongoTemplate;

	Map<String, Customer> customerMap = new HashMap<>();

	@BeforeEach
	void setUp() {
		customerMap.put("hpq", createCustomer("Heinz", "Paul", "Quack"));
		customerMap.put("hps", createCustomer("Heinz", "Paul", "Schauder"));
		customerMap.put("hjs", createCustomer("Heinz", "Jodokus", "Schauder"));
		customerMap.put("hjq", createCustomer("Heinz", "Jodokus", "Quack"));
		customerMap.put("ajq", createCustomer("Alfred", "Jodokus", "Quack"));
		customerMap.put("apq", createCustomer("Alfred", "Paul", "Quack"));
		customerMap.put("aps", createCustomer("Alfred", "Paul", "Schauder"));
		customerMap.put("ajs", createCustomer("Alfred", "Jodokus", "Schauder"));
	}

	@AfterEach
	void cleanUp() {
		customers.deleteAll();
	}

	@ParameterizedTest
	@MethodSource
	void byFixedQuery(Fixture f) {

		assertThat(customers.find(f.first, f.middle, f.last)).containsExactlyInAnyOrder(Arrays.stream(f.result).map(customerMap::get).toArray(Customer[]::new));
	}

	static List<Fixture> byFixedQuery() {

		return asList(
				f("Heinz", null, null, "hpq", "hps", "hjs", "hjq"),
				f("Heinz", "Jodokus", "", "hjs", "hjq"),
				f("Heinz", "Jodokus", "Quack", "hjq")
		);
	}

	static Fixture f(String first, String middle, String last, String... result) {
		return new Fixture(first, middle, last, result);
	}

	Customer createCustomer(String first, String middle, String last) {
		Customer customer = new Customer();
		customer.first = first;
		customer.middle = middle;
		customer.last = last;
		return customers.save(customer);
	}

	static class Fixture {
		final String first;
		final String middle;
		final String last;
		final String[] result;

		public Fixture(String first, String middle, String last, String[] result) {

			this.first = first;
			this.middle = middle;
			this.last = last;
			this.result = result;
		}
	}
}
