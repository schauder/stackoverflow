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
package de.schauderhaft.example.removereference;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;

class Person {
	@Id
	Long id;
	String name;
	@MappedCollection(idColumn = "PERSON_ID")
	final Set<PersonAddress> addresses;

	Person(String name) {
		this(null, name, new HashSet<>());
	}

	@PersistenceCreator
	Person(Long id, String name, Set<PersonAddress> addresses) {

		this.id = id;
		this.name = name;
		this.addresses = addresses;
	}

	void addAddress(Address address) {
		addresses.add(new PersonAddress(address.id));
	}

	void removeAddress(Address address) {
		addresses.remove(new PersonAddress(address.id));
	}

	@Override
	public String toString() {
		return "Person[" +
				"name=" + name + ", " +
				"adresses=" + addresses + ']';
	}


}
