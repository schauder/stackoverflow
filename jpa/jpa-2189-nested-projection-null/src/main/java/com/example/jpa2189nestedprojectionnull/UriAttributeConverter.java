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

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.net.URI;

@Converter(autoApply = true)
public class UriAttributeConverter implements AttributeConverter<URI, String> {

	@Override
	public String convertToDatabaseColumn(URI entityValue) {
		return (entityValue == null) ? null : entityValue.toString();
	}

	@Override
	public URI convertToEntityAttribute(String databaseValue) {
		return (org.springframework.util.StringUtils.hasLength(databaseValue) ? URI.create(databaseValue.trim()) : null);
	}
}
