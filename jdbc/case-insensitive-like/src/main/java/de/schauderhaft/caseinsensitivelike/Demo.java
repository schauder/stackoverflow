package de.schauderhaft.caseinsensitivelike;

import org.springframework.data.annotation.Id;

record Demo (@Id Long id, String name){
	static Demo demo(String name) {
		return new Demo(null, name);
	}
}
