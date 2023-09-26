package de.schauderhaft.caseinsensitivelike;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;

import java.util.List;

import static de.schauderhaft.caseinsensitivelike.Demo.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CaseInsensitiveLikeApplicationTests {

	@Autowired
	JdbcAggregateTemplate jdbc;

	@Test
	void likeWithIgnoreCase() {

		assertThat(jdbc).isNotNull();

		jdbc.saveAll(List.of(demo("ONE"), demo("drone"), demo("donE"), demo("other")));

		assertThat(getList()).extracting(d -> d.name()).containsExactlyInAnyOrder("ONE", "drone", "donE");
		assertThat(getPage2(Pageable.ofSize(2))).hasSize(2);
	}

	Iterable<Demo> getList() {

		Criteria criteria = Criteria.where("name").like("%" + "ne" + "%").ignoreCase(true);
		Query query = Query.query(criteria);

		return jdbc.findAll(query, Demo.class);
	}

	Iterable<Demo> getPage1(Pageable pageable) {

		Criteria criteria = Criteria.where("name").like("%" + "ne" + "%").ignoreCase(true);
		Query query = Query.query(criteria);

		query.with(pageable);

		return jdbc.findAll(query, Demo.class);
	}

	Iterable<Demo> getPage2(Pageable pageable) {

		Criteria criteria = Criteria.where("name").like("%" + "ne" + "%").ignoreCase(true);
		Query query = Query.query(criteria);

		return jdbc.findAll(query, Demo.class, pageable);
	}
}
