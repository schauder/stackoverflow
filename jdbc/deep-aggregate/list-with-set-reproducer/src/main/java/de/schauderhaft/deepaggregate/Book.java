package de.schauderhaft.deepaggregate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;

record Book(@Id Long id,
			@MappedCollection(idColumn = "BOOK_ID", keyColumn = "COPY_NUMBER") List<BookCopy> copies) {
}