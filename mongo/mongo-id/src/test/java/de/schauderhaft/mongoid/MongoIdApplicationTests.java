package de.schauderhaft.mongoid;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

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

		Customer customer = new Customer();
		customer.name = "Jens";
		customer.fruits.add(new Fruit("pa", "Pineapple"));
		customer.fruits.add(new Fruit("ba", "Banana"));

		Customer saved = customers.save(customer);

		assertThat(saved.fruits).extracting(f -> f.id).containsExactly("pa", "ba");

		Customer reloaded = customers.findById(customer.id).get();
		assertThat(reloaded.fruits).extracting(f -> f.id).containsExactly("pa", "ba");

		SoftAssertions.assertSoftly(softly ->
				mongoTemplate.getCollection("customer").find()
						.forEach(d -> {
							List<Document> fruits = (List<Document>)d.get("fruits");
							System.out.println(fruits.get(0));
							softly.assertThat(fruits).describedAs("id of fruits in " + d.toJson() + " should not be null")
									.extracting(f -> f.get("id"))
									.doesNotContainNull();
						})
		);
	}
}
