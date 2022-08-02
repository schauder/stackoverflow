package de.schauderhaft.poc.oracle.storedprocedure;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.Instant;

 final class SomeEntity {
	@Id
	final Long id;
	final String name;

	@PersistenceCreator
	 SomeEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	 SomeEntity(String name) {
		 this(null, name);
	 }

}
