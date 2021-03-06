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
package de.schauderhaft.mongoinwithnull;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, String> {

	@Query("{firstName : ?0, lastName : {$in: ?1}}")
	List<Customer> findByFirstNameAndLastNameIn(String first, List<String> lasts);

	@Query("{firstName : ?0, lastName : {$in: [?1, null]}}")
	List<Customer> findByFirstNameAndLastNameOrNull(String first, String last);

	@Query("{firstName : {$in: ?0}}")
	List<Customer> findByFirstNameIn(List<String> firsts);
}
