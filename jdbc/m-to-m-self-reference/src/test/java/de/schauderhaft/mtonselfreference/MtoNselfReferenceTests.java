package de.schauderhaft.mtonselfreference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.*;

@DataJdbcTest
class MtoNselfReferenceTests {

	@Autowired
	AsyncTaskRepository repo;

	@Test
	void test() {
		AsyncTask one = repo.save(new AsyncTask("one"));
		AsyncTask two = repo.save(new AsyncTask("two"));

		AsyncTask three = new AsyncTask("three");
		three.addDependent(one);
		three.addDependent(two);
		repo.save(three);

		one.addDependent(two);
		repo.save(one);

		two.addDependent(two);
		repo.save(two);

	}

}
