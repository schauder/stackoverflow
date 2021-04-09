package com.example.jpa2189nestedprojectionnull;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.jdbc.datasource.init.ScriptUtils.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql(scripts = {"classpath:scripts/schema.sql", "classpath:scripts/data.sql"}, //
		config = @SqlConfig( //
				separator = EOF_STATEMENT_SEPARATOR))
class Jpa2189NestedProjectionNullApplicationTests {

	@Container
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:12-alpine");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}

	@Autowired
	ProductDetailsRepository detailsRepository;

	@Autowired
	JdbcTemplate template;

	@Autowired
	EntityManager em;

	@Test
	void fetchProjectedProductWithDetailsTest() {

		detailsRepository.findAll().forEach(System.out::println);

		assertThat(detailsRepository.findById("1fb9e691033d4092b32699088d401ec9")).isPresent();

		var storeFrontProduct = detailsRepository.findById(
				"1fb9e691033d4092b32699088d401ec9", StoreAdminProduct.class);

		assertThat(storeFrontProduct).isPresent()
				.get(InstanceOfAssertFactories.type(StoreAdminProduct.class))
				.hasFieldOrPropertyWithValue("costPrice", 350.0)
				.extracting(StoreAdminProduct::getProduct).isNotNull() // Fails here
				.hasFieldOrPropertyWithValue("featuredMedia", "/files/products/jordans.jpeg");
	}


	@Test
	void testCriteriaQuery() {

		CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery(Object.class);
		Root<ProductDetails> root = query.from(ProductDetails.class);
		query = query.multiselect(root.get("id"), root.get("costPrice"), root.get("product"));

		List<Object> resultList = em.createQuery(query).getResultList();

		SoftAssertions.assertSoftly(softly -> resultList.forEach(o -> {
			assertThat(o).isInstanceOf(Object[].class);
			softly.assertThat(((Object[]) o)[2]).isNotNull();
		}));
	}


	@Test
	void testTheSelect() {
		String sql = "select\n" +
				"        productdet0_.id as col_0_0_,\n" +
				"        productdet0_.cost_price as col_1_0_,\n" +
				"        product1_.id as col_2_0_,\n" +
				"        product1_.id as id1_0_,\n" +
				"        product1_.created_by as created_2_0_,\n" +
				"        product1_.created_date as created_3_0_,\n" +
				"        product1_.last_modified_by as last_mod4_0_,\n" +
				"        product1_.last_modified_date as last_mod5_0_,\n" +
				"        product1_.description as descript6_0_,\n" +
				"        product1_.featured_media as featured7_0_,\n" +
				"        product1_.name as name8_0_,\n" +
				"        product1_.price as price9_0_,\n" +
				"        product1_.status as status10_0_ \n" +
				"    from\n" +
				"        product_details productdet0_ \n" +
				"    left outer join\n" +
				"        product product1_ \n" +
				"            on productdet0_.id=product1_.id \n" +
				"    where\n" +
				"        productdet0_.id=?";

		template.query(sql, resultSet -> {
			int columnCount = resultSet.getMetaData().getColumnCount();
			for (int col = 1; col <= columnCount; col++) {
				System.out.print("\t " + resultSet.getObject(col));
			}
		}, "1fb9e691033d4092b32699088d401ec9");
	}
}
