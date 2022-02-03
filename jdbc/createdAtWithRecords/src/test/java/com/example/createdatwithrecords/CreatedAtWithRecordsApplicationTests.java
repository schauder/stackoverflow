package com.example.createdatwithrecords;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.SoftAssertions.*;

@SpringBootTest
class CreatedAtWithRecordsApplicationTests {

	@Autowired
	ClassRepository classes;

	@Test
	void classes() {
		ClassEntity saved = classes.save(new ClassEntity(null, "test 1", null));

		assertSoftly(softly -> {
			softly.assertThat(saved).isNotNull();
			softly.assertThat(saved.id).isNotNull();
			softly.assertThat(saved.createdAt).isNotNull();
			softly.assertThat(saved.name).isEqualTo("test 1");
		});
	}


	@Autowired
	RecordRepository records;

	@Test
	void records() {
		RecordEntity saved = records.save(new RecordEntity(null, "test 1", null));

		assertSoftly(softly -> {
			softly.assertThat(saved).isNotNull();
			softly.assertThat(saved.id()).isNotNull();
			softly.assertThat(saved.createdAt()).isNotNull();
			softly.assertThat(saved.name()).isEqualTo("test 1");
		});
	}



}
