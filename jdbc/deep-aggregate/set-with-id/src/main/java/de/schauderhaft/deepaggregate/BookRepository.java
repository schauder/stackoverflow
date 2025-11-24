package de.schauderhaft.deepaggregate;

import org.springframework.data.repository.ListCrudRepository;

public interface BookRepository extends ListCrudRepository<Book, Long> {
}
