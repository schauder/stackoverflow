package com.example.createdatwithrecords;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.util.Objects;

public final class ClassEntity {
	@Id
	final Long id;
	final String name;
	@CreatedDate
	final Instant createdAt;

	public ClassEntity(Long id, String name, Instant createdAt) {
		this.id = id;
		this.name = name;
		this.createdAt = createdAt;
	}

	ClassEntity withCreatedAt(Instant createdAt) {
		return new ClassEntity(id, name, createdAt);
	}

}
