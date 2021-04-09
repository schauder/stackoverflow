package de.schauderhaft.mongoid;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class MongoInWithNullApplicationTests {

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

	Customer jensSchauder;
	Customer peterSchauder;
	Customer jensMeier;
	Customer peterMeier;
	Customer jens;
	Customer peter;
	Customer schauder;
	Customer meier;

	@BeforeEach
	void setup(){

		jensSchauder = customers.save(createCustomer("Jens", "Schauder"));
		peterSchauder = customers.save(createCustomer("Peter", "Schauder"));
		jensMeier = customers.save(createCustomer("Jens", "Meier"));
		peterMeier = customers.save(createCustomer("Peter", "Meier"));
		jens = customers.save(createCustomer("Jens", null));
		peter = customers.save(createCustomer("Peter", null));
		schauder = customers.save(createCustomer(null, "Schauder"));
		meier = customers.save(createCustomer(null, "Meier"));

		customers.saveAll(asList(jens, jensMeier, jensSchauder, peterSchauder, peter, peterMeier, meier, schauder));

	}

	@AfterEach
	void cleanUp() {
		customers.deleteAll();
	}

	@Test
	void findByFirstNameAndLastNamesWithNull() {
		assertThat(customers.findByFirstNameAndLastNameIn("Jens", asList("Schauder", null)))
				.containsExactlyInAnyOrder(jens, jensSchauder);
	}

	@Test
	void findByFirstNameAndLastNameOrNull() {
		assertThat(customers.findByFirstNameAndLastNameOrNull("Jens", "Schauder"))
				.containsExactlyInAnyOrder(jens, jensSchauder);
	}

	@Test
	void findByFirstNameInWithNull() {
		assertThat(customers.findByFirstNameIn(asList("Jens", null)))
				.containsExactlyInAnyOrder(jens, jensSchauder, schauder, jensMeier, meier);
	}

	private Customer createCustomer(String firstName, String lastName) {
		Customer customer = new Customer();
		customer.firstName = firstName;
		customer.lastName = lastName;
		return customer;
	}
}
