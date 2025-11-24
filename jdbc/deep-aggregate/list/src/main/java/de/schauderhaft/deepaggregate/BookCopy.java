package de.schauderhaft.deepaggregate;

import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;

record BookCopy(
		@MappedCollection(idColumn = "BOOK_ID") List<BookCopyQualityControl> qualityControls) {
}