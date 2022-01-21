/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.schauderhaft.threeway;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;

class Student {

		@Id
		final Long id;
		String name;

		@MappedCollection(idColumn = "STUDENT_ID", keyColumn = "COURSE_ID")
		Set<CourseRef> courses = new HashSet<>();

	Student(Long id) {
		this.id = id;
	}

	public static Student create(String name) {
		final Student student = new Student(null);
		student.name = name;
		return student;
	}

	void addCourse(Course course) {
		final CourseRef ref = new CourseRef();
		ref.courseId = AggregateReference.to(course.id);
		courses.add(ref);
	}

	public void addScore(Course course, int score) {
		courses.stream()
				.filter(c -> c.courseId.getId().equals(course.id))
				.findFirst()
				.orElseThrow()
				.testScores.add(TestScore.create(90));
	}
}
