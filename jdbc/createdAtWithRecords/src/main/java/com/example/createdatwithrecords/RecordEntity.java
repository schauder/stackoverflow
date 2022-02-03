package com.example.createdatwithrecords;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.LocalDateTime;

public record RecordEntity(@Id Long id, String name, @CreatedDate LocalDateTime createdAt) {
}
