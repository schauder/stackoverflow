package de.schauderhaft.threeway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
class StudentRepositoryTest {

	@Autowired
	StudentRepository students;

	@Autowired
	CourseRepository courses;

	Student jens = null;

	@BeforeEach
	void setup() {

		Course physics = courses.save(Course.create("Physics"));
		Course math = courses.save(Course.create("Math"));
		Course informatics = courses.save(Course.create("Informatics"));

		jens = Student.create("Jens");

		jens.addCourse(physics);
		jens.addCourse(math);
		jens.addCourse(informatics);

		jens = students.save(jens);

	}

	@Test
	void testAddTestScore() {

		Student student = students.findById(jens.id).orElseThrow();
		assertNotNull(student);
		Course math = courses.findByName("Math");
		assertNotNull(math);

		student.addScore(math, 90);

		students.save(student);
	}

}
