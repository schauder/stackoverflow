package de.schauderhaft.example.removereference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RemoveReferenceApplicationTests {

	@Autowired
	Persons persons;
	@Autowired
	Addresses addresses;


	@Test
	void insertAndRemoveReferences() {

		Address maine = addresses.save(new Address("698 Candlewood Lane, Cabot Cove, Maine"));
		Address london = addresses.save(new Address("221B Baker St., London"));
		Address washington = addresses.save(new Address("1600 Pennsylvania Avenue, Washington, D.C., USA"));


		Person jessica = persons.save(createPerson("Jessica Fletcher", maine));
		Person sherlock = persons.save(createPerson("Sherlock Holmes", maine, london));
		Person john = persons.save(createPerson("John F Kennedy", maine, london, washington));

		assertThat(persons.findAll())
				.extracting(p -> p.name, p -> p.addresses.size())
				.containsExactlyInAnyOrder(
						tuple("Jessica Fletcher", 1),
						tuple("Sherlock Holmes", 2),
						tuple("John F Kennedy", 3)
				);

		sherlock.removeAddress(maine);

		john.removeAddress(maine);
		john.removeAddress(london);
		john.removeAddress(washington);

		persons.save(sherlock);
		persons.save(john);

		assertThat(persons.findAll())
				.extracting(p -> p.name, p -> p.addresses.size())
				.containsExactlyInAnyOrder(
						tuple("Jessica Fletcher", 1),
						tuple("Sherlock Holmes", 1),
						tuple("John F Kennedy", 0)
				);

	}

	private Person createPerson(String name, Address... addresses) {
		Person person = new Person(name);
		Arrays.stream(addresses).forEach(person::addAddress);

		return person;
	}

}
