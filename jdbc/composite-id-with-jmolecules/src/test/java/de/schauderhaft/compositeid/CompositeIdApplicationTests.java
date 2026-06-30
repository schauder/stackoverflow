package de.schauderhaft.compositeid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class CompositeIdApplicationTests {

	@Autowired
	ServiceRepo repo;

	@Test
	void saveService() {
		repo.save(new Service(new ServiceId(UUID.randomUUID(), UUID.randomUUID()), "name", null));
	}

}
