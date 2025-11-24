package de.schauderhaft.deepaggregate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class DeepAggregateApplicationTests {

	@Autowired
	BookRepository books;

	@Test
	void storeBooks() {
		books.save(
				new Book(
						null,
						List.of(
								new BookCopy(
										Set.of(
												new BookCopyQualityControl(23L),
												new BookCopyQualityControl(4711L)
										)
								)
						)
				)
		);
	}

}
