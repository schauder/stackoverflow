package de.schauderhaft.compositeid;

import org.springframework.data.repository.ListCrudRepository;

public interface ServiceRepo extends ListCrudRepository<Service, ServiceId> {
}
