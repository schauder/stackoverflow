package de.schauderhaft.de.constructorexpressionwithnestedreference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ConstructorExpressionWithNestedReferenceApplicationTests {

	@Autowired
	SomeEntityRepository ents;

	@Test
	@Transactional
	void testDtos() {

		createEnts();

		assertThat(ents.findDto()).extracting(Dto::toString).containsExactlyInAnyOrder("ents name - parents name", "parents name - null");

	}

	@Test
	@Transactional
	void testDtosInnerJoin() {

		createEnts();

		assertThat(ents.findDtoInnerJoin()).extracting(Dto::toString).containsExactly("ents name - parents name");

	}

	@Test
	@Transactional
	void testEntities() {

		createEnts();

		assertThat(ents.findEntities()).extracting(e -> e.name).containsExactlyInAnyOrder("ents name", "parents name");

	}

	private void createEnts() {

		SomeEntity ent = new SomeEntity();
		ent.name = "ents name";
		ent.parent = new SomeEntity();
		ent.parent.name = "parents name";


		ents.saveAll(asList(ent, ent.parent));
	}

}
