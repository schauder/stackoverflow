package de.schauderhaft.example.contains;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ApplicationTests {

	@Autowired
	EmployeeRepository emps;

	@Autowired
	JdbcTemplate jdbc;

	@Test
	@Transactional
	void testHobbies() {

		createEmployees();


		assertThat(
				emps.findByHobbies("running")
		).extracting(e -> e.fullName).containsExactlyInAnyOrder("Christoph", "Jens");

	}

	private void createEmployees() {
		emps.saveAllAndFlush(asList(
				createEmployee("Jens", "climbing", "running", "board games"),
				createEmployee("Christoph", "running"),
				createEmployee("Petra", "gardening")
		));
	}

	@Test
	@Transactional
	void testContaining() {

		createEmployees();


		assertThat(
				emps.findByHobbiesContaining("running")
		).extracting(e -> e.fullName).containsExactlyInAnyOrder("Christoph", "Jens");

	}


	private Employee createEmployee(String name, String... hobbies) {

		final Employee employee = new Employee();
		employee.fullName = name;
		employee.hobbies = asList(hobbies);

		return employee;
	}
}
