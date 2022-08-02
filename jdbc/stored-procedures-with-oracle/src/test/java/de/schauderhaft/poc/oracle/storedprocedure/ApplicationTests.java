package de.schauderhaft.poc.oracle.storedprocedure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.*;

@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class})
class ApplicationTests {

	@Autowired
	SomeEntityRepository classes;

	@Autowired
	JdbcTemplate jdbc;

	@Test
	void testSchema() {
		final List<Map<String, Object>> tables = jdbc.queryForList("""
				SELECT  TABLE_NAME as table_name FROM USER_TABLES
				""");

		tables.forEach(row -> {
//			row.forEach((n, v) -> System.out.println(n + " - " + v));
//			System.out.println();
			System.out.println(row.get("TABLE_NAME"));
		});
	}


	@Test
	void test() {
		SomeEntity saved = classes.save(new SomeEntity(null, "test 1"));

		assertSoftly(softly -> {
			softly.assertThat(saved).isNotNull();
			softly.assertThat(saved.id).isNotNull();
			softly.assertThat(saved.name).isEqualTo("test 1");
		});
	}

}
