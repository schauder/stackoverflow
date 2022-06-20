/*
 * Copyright 2021 the original author or authors.
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
package de.schauderhaft.de.anyinspec;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String fullName = "";

	@ManyToMany
	@JoinTable(
			name = "employee_team",
			joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "team_id", referencedColumnName = "id")}
	)
	List<Team> teams = new ArrayList<>();

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", fullName='" + fullName + '\'' +
				", teams=" + teams +
				'}';
	}
}
