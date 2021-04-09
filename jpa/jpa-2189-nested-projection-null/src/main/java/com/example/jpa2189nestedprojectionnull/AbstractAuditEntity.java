package com.example.jpa2189nestedprojectionnull;

import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity implements Auditable<String, String, OffsetDateTime>, Serializable {
	private static final long serialVersionUID = 1111111L;
	@Id
	@GeneratedValue
	private String id;
	private OffsetDateTime createdDate;
	private OffsetDateTime lastModifiedDate;
	private String createdBy;
	private String lastModifiedBy;
	@Transient
	private boolean isNew = true;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Optional<String> getCreatedBy() {
		return Optional.of(this.createdBy);
	}

	@Override
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Optional<String> getLastModifiedBy() {
		return Optional.of(this.lastModifiedBy);
	}

	@Override
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public Optional<OffsetDateTime> getCreatedDate() {
		return Optional.of(this.createdDate);
	}

	@Override
	public void setCreatedDate(OffsetDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public Optional<OffsetDateTime> getLastModifiedDate() {
		return Optional.of(this.lastModifiedDate);
	}

	@Override
	public void setLastModifiedDate(OffsetDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	@PrePersist
	@PostLoad
	void markNotNew() {
		this.isNew = false;
	}
}