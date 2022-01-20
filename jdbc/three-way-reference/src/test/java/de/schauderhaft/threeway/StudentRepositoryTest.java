package de.schauderhaft.threeway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
class StudentRepositoryTest {

	@Autowired
	StudentRepository repository;

	@Test
	void testAddTestScore() {

		Student student = repository.findById(1L).get();
		assertNotNull(student);

		Set<CourseRef> courses = student.courses;
		CourseRef course = courses.stream().filter(c -> c.courseId == 2).findFirst().orElse(null);
		assertNotNull(course);

		courses.remove(course);
		course.testScores.add(TestScore.create(90));
		courses.add(course);
		student.courses = courses;
		repository.save(student);
	}

}
