package de.schauderhaft.deepaggregate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

record Book(@Id Long id,
			@MappedCollection(idColumn = "BOOK_ID") Set<BookCopy> copies) {
}