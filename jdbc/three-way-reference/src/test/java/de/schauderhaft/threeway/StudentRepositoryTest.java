package de.schauderhaft.threeway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
class StudentRepositoryTest {

	@Autowired
	StudentRepository students;

	@Autowired
	CourseRepository courses;

	Student jens = null;
	Course math;

	@BeforeEach
	void setup() {

		final Course physics = courses.save(Course.create("Physics"));
		math = courses.save(Course.create("Math"));
		final Course informatics = courses.save(Course.create("Informatics"));

		jens = new Student();
		jens.studentName = "Jens";

		jens.add(physics);
		jens.add(math);
		jens.add(informatics);
		students.save(jens);

	}

	@Test
	void testAddTestScore() {

		Student student = students.findById(jens.studentId).get();
		assertNotNull(student);

		Set<CourseRef> courses = student.courses;
		CourseRef course = courses.stream().filter(c -> c.courseId == math.courseId).findFirst().orElse(null);
		assertNotNull(course);

		courses.remove(course);
		course.testScores.add(TestScore.create(90));
		courses.add(course);
		student.courses = courses;
		students.save(student);
	}

}
