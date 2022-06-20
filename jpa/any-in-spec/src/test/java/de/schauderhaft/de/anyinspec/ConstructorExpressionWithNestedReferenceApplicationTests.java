package de.schauderhaft.de.anyinspec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AnyInSpecApplicationTests {

	@Autowired
	EmployeeRepository emps;

	@Autowired
	TeamRepository teams;

	@Test
	@Transactional
	void test() {

		final Team one = teams.save(createTeam("one"));
		final Team two = teams.save(createTeam("two"));

		final Employee ann = emps.save(createEmployee("Ann"));
		ann.teams.add(one);
		final Employee beth = emps.save(createEmployee("Beth"));
		beth.teams.add(two);
		final Employee carl = emps.save(createEmployee("Carl"));
		carl.teams.add(one);
		carl.teams.add(two);
		final Employee dave = emps.save(createEmployee("Carl"));


		assertThat(
				emps.findAll(byTeams(asList(one.id, two.id)))
		).containsExactlyInAnyOrder(ann, beth, carl);

		assertThat(
				emps.findAll(byTeams(asList(two.id)))
		).containsExactlyInAnyOrder(beth, carl);

	}

	private Specification<Employee> byTeams(List<Long> ids) {

		Specification<Employee> spec = Specification.where(null);

		for (Long id : ids) {
			spec = spec.or(createSingleIn(id));
		}

		return spec;
	}

	private Specification<Employee> createSingleIn(Long id) {
		return (root, query, cb) -> {
			query.distinct(true); // using an ugly side effect to avoid the ugly side effect that where clauses cause duplicates in JPA.
			return cb.literal(id).in(root.join("teams").get("id"));
		};
	}

	private Employee createEmployee(String name) {
		final Employee employee = new Employee();
		employee.fullName = name;
		return employee;
	}

	private Team createTeam(String name) {
		final Team team = new Team();
		team.name = name;
		return team;
	}
}
