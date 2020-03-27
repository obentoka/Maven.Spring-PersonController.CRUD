package io.zipcoder.crudapp.ropositories;

import io.zipcoder.crudapp.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
