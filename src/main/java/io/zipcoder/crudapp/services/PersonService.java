package io.zipcoder.crudapp.services;

import io.zipcoder.crudapp.models.Person;
import io.zipcoder.crudapp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    public Person find(Long id) {
        return repository.findOne(id);
    }

    public Person create(Person person) {
        return repository.save(person);
    }

    public Person update(Long id, Person newPersonData) {
        if(find(id) != null) {
            Person originalPerson = repository.findOne(id);
            originalPerson.setId(id);
            originalPerson.setFirstName(newPersonData.getFirstName());
            originalPerson.setLastName(newPersonData.getLastName());
            return repository.save(originalPerson);
        }
        else {
            return create(newPersonData);
        }
    }

    public Boolean delete(Long id) {
        if(find(id) != null) {
            repository.delete(id);
            return true;
        }
        return false;
    }
}
