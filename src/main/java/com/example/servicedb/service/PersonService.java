package com.example.servicedb.service;

import com.example.servicedb.model.Person;
import com.example.servicedb.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> getAllPersons() {
        log.info("Fetching all persons from repository");
        List<Person> persons = personRepository.findAll();
        log.debug("Retrieved {} persons", persons.size());
        return persons;
    }

    public Optional<Person> getPersonById(Long id) {
        log.info("Fetching person with ID: {}", id);
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            log.debug("Found person: {}", person.get().getName());
        } else {
            log.warn("Person with ID {} not found", id);
        }
        return person;
    }

    public Person createPerson(Person person) {
        log.info("Creating person: {}", person.getName());
        Person savedPerson = personRepository.save(person);
        log.debug("Created person with ID: {}", savedPerson.getId());
        return savedPerson;
    }

    public Person updatePerson(Long id, Person personDetails) {
        log.info("Updating person with ID: {}", id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Person with ID {} not found for update", id);
                    return new RuntimeException("Person not found with ID: " + id);
                });
        person.setName(personDetails.getName());
        person.setBiography(personDetails.getBiography());
        person.setPhotograph(personDetails.getPhotograph());
        Person updatedPerson = personRepository.save(person);
        log.debug("Updated person: {}", updatedPerson.getName());
        return updatedPerson;
    }

    public void deletePerson(Long id) {
        log.info("Deleting person with ID: {}", id);
        if (!personRepository.existsById(id)) {
            log.warn("Person with ID {} not found for deletion", id);
            throw new RuntimeException("Person not found with ID: " + id);
        }
        personRepository.deleteById(id);
        log.debug("Deleted person with ID: {}", id);
    }
}