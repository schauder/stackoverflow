/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.jpa2189nestedprojectionnull;

import java.net.URI;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@org.hibernate.annotations.TypeDef(name = "list-array", typeClass = com.vladmihalcea.hibernate.type.array.ListArrayType.class)
public class Product extends AbstractAuditEntity {

	private static final long serialVersionUID = 222222L;
	private String name;
	@Column(columnDefinition = "text")
	private String description;
	private URI featuredMedia;
	private Double price;
	@Enumerated(EnumType.STRING)
	private ProductStatus status = ProductStatus.DRAFT;
	@org.hibernate.annotations.Type(type = "list-array")
	@Column(columnDefinition = "varchar[]")
	private List<String> tags;
}