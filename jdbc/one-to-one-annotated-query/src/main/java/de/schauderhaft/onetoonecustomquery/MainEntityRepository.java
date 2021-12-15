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
package de.schauderhaft.onetoonecustomquery;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MainEntityRepository extends CrudRepository<MainEntity, Long> {
	@Query("SELECT * FROM main_entities WHERE another_id = :anotherId")
	Optional<MainEntity> findWithOutJoinByAnotherId(@Param("anotherId") String anotherId);

	@Query("SELECT M.ID, M.ANOTHER_ID, S.ID AS SECONDENTITY_ID, S.MAIN_ENTITY_ID AS SECONDENTITY_MAIN_ENTITY_ID " +
			"FROM MAIN_ENTITIES M " +
			"JOIN SECOND_ENTITIES S " +
			" ON M.ID = S.MAIN_ENTITY_ID " +
			"WHERE ANOTHER_ID = :anotherId")
	Optional<MainEntity> findWithJoinByAnotherId(String anotherId);

}
