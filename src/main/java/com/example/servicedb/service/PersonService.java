package com.example.servicedb.service;

import com.example.servicedb.model.Person;
import com.example.servicedb.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() { return personRepository.findAll(); }
    public Optional<Person> getPersonById(Long id) { return personRepository.findById(id); }
    public Person createPerson(Person person) { return personRepository.save(person); }
    public Person updatePerson(Long id, Person personDetails) {
        Person person = personRepository.findById(id).orElseThrow();
        person.setName(personDetails.getName());
        person.setBiography(personDetails.getBiography());
        person.setPhotograph(personDetails.getPhotograph());
        return personRepository.save(person);
    }
    public void deletePerson(Long id) { personRepository.deleteById(id); }
}
