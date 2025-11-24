package de.schauderhaft.deepaggregate;

import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

record BookCopy(
		@MappedCollection(idColumn = "BOOK_ID") Set<BookCopyQualityControl> qualityControls) {
}