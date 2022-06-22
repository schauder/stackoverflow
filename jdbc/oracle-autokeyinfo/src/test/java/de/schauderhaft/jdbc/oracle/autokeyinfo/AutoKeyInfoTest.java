package de.schauderhaft.jdbc.oracle.autokeyinfo;/*
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

import oracle.jdbc.pool.OracleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.testcontainers.containers.OracleContainer;

import java.sql.SQLException;
import java.util.HashMap;

public class AutoKeyInfoTest {
	static final OracleDataSource ds;

	static {
		try {
			ds = new OracleDataSource();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@BeforeAll
	static void setup() {
		OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe:21.3.0-slim");
		oracle.start();

		ds.setURL(oracle.getJdbcUrl());
		ds.setUser(oracle.getUsername());
		ds.setPassword(oracle.getPassword());
	}

	@Test
	void test() throws SQLException {

		final String tableName = "MyTable";

		final JdbcTemplate jdbc = new JdbcTemplate(ds);

		jdbc.execute("create table " + tableName + " ( " +
				"id  NUMBER GENERATED ALWAYS AS IDENTITY, " +
				"name varchar2(200) " +
				")"
		);

		final BatchJdbcOperations batch = new BatchJdbcOperations(jdbc);


		final HashMap<String, Object> keyMap = new HashMap<>();
		final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();


		final SqlParameterSource[] args = new SqlParameterSource[2];
		args[0] = new MapSqlParameterSource("name", "test1");
		args[1] = new MapSqlParameterSource("name", "test2");

		batch.batchUpdate("insert into " + tableName + "(name) values (:name)", args, keyHolder, new String[]{"ID"});

	}
}
