package de.schauderhaft.onetoonecustomquery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.*;

@DataJdbcTest
class OneToOneWithCustomQueryTests {

	@Autowired
	MainEntityRepository repo;
	MainEntity main = new MainEntity();

	@Test
	void withOutSelectingOneToOneRelationship() {

		assertThat(main.anotherId).isNotNull();

		final MainEntity reloaded = repo.findWithOutJoinByAnotherId(main.anotherId).orElseThrow();

		assertThat(reloaded).isNotNull();

		// referenced entity is null, since it was not included in the query.
		assertThat(reloaded.secondEntity).isNull();
	}

	@Test
	void withSelectingOneToOneRelationship() {

		assertThat(main.anotherId).isNotNull();

		final MainEntity reloaded = repo.findWithJoinByAnotherId(main.anotherId).orElseThrow();

		assertThat(reloaded).isNotNull();

		assertThat(reloaded.secondEntity).isNotNull();
		assertThat(reloaded.secondEntity.id).isNotNull();
		assertThat(reloaded.secondEntity.mainEntityId).isNotNull();
	}

	@BeforeEach
	void before() {

		main.secondEntity = new SecondEntity();
		main.anotherId = "42";
		repo.save(main);
	}
}
