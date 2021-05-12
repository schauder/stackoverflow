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
package de.schauderhaft.mongoid;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, String> {
	@Query("{$and: [" +
			"{$or: [{'first' : ?0}, { $where: '\\'?0\\' == \\'null\\'' }, { $where: '\\'?0\\' == \\'\\'' }]}," +
			"{$or: [{'middle' : ?1}, { $where: '\\'?1\\' == \\'null\\'' }, { $where: '\\'?1\\' == \\'\\'' }]}," +
			"{$or: [{'last' : ?2}, { $where: '\\'?2\\' == \\'null\\'' }, { $where: '\\'?2\\' == \\'\\'' }]}" +
			"]}")
	List<Customer> find(String first, String middle, String last);
}
