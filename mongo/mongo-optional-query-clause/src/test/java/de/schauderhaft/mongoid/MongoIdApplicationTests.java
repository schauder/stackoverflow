package de.schauderhaft.mongoid;

import org.assertj.core.api.SoftAssertions;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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

	@AfterEach
	void cleanUp() {
		customers.deleteAll();
	}

	@Test
	void example() {

		Customer hpq = createCustomer("Heinz", "Paul", "Quack");
		Customer hps = createCustomer("Heinz", "Paul", "Schauder");
		Customer hjs = createCustomer("Heinz", "Jodokus", "Schauder");
		Customer hjq = createCustomer("Heinz", "Jodokus", "Quack");
		Customer ajq = createCustomer("Alfred", "Jodokus", "Quack");
		Customer apq = createCustomer("Alfred", "Paul", "Quack");
		Customer aps = createCustomer("Alfred", "Paul", "Schauder");
		Customer ajs = createCustomer("Alfred", "Jodokus", "Schauder");

		SoftAssertions.assertSoftly(softly -> {
			softly.assertThat(customers.find("Heinz", null, null)).containsExactlyInAnyOrder(hpq, hps, hjs, hjq);
			softly.assertThat(customers.find("Heinz", "Jodokus", "")).containsExactlyInAnyOrder(hjs, hjq);
			softly.assertThat(customers.find("Heinz", "Jodokus", "Quack")).containsExactlyInAnyOrder( hjq);
		});
	}

	private Customer createCustomer(String first, String middle, String last) {
		Customer customer = new Customer();
		customer.first = first;
		customer.middle = middle;
		customer.last = last;
		return customers.save(customer);
	}
}
