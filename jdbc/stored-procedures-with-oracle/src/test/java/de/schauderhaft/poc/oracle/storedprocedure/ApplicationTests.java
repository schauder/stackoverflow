package de.schauderhaft.poc.oracle.storedprocedure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
class ApplicationTests {

	@Autowired
	SomeEntityRepository repository;

	@Autowired
	JdbcTemplate jdbc;

	@Test
	void testSchema() {
		printTables();

		printCompileErrors();
	}

	private void printCompileErrors() {
		final List<Map<String, Object>> lines = jdbc.queryForList("""
				SELECT  TEXT
				  FROM  USER_ERRORS
				  ORDER BY NAME, LINE
				""");

		lines.forEach(line -> System.out.println(line.get("TEXT")));
	}

	private void printTables() {
		final List<Map<String, Object>> tables = jdbc.queryForList("""
				SELECT  TABLE_NAME as table_name FROM USER_TABLES
				""");

		tables.forEach(row -> {
			System.out.println(row.get("TABLE_NAME"));
		});
	}


	@Test
	void test() {
		SomeEntity saved = repository.save(new SomeEntity(null, "test 1"));

		assertSoftly(softly -> {
			softly.assertThat(saved).isNotNull();
			softly.assertThat(saved.id).isNotNull();
			softly.assertThat(saved.name).isEqualTo("test 1");
		});
	}


	@Test
	void testNoInNoOutNoReturn() {

		repository.noInNoOutNoReturn();

		final Iterable<SomeEntity> entities = repository.findAll();

		assertThat(entities).extracting(e -> e.name).contains("NO_IN_NO_OUT_NO_RETURN");
	}

	@Test
	void testSimpleValueOut() {

		assertThat(repository.simpleValueOut()).isEqualTo(23);
	}
}
