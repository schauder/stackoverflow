package de.schauderhaft.compositeid;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@AggregateRoot
public class Service {

	@Id
	private final ServiceId id;
	private String name;
	@Version
	private Integer version;

	public Service(ServiceId id, String name, Integer version) {
		this.id = id;
		this.name = name;
		this.version = version;
	}

	public ServiceId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getVersion() {
		return version;
	}
}
