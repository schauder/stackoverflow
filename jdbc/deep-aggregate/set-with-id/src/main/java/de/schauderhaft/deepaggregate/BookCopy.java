package de.schauderhaft.deepaggregate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

record BookCopy(
		@Id int copy_number, // this now needs to be globally unique in the table
		@MappedCollection(idColumn = "BOOK_COPY_NUMBER") Set<BookCopyQualityControl> qualityControls) {
}