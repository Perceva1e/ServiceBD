package com.example.servicedb.controller;

import com.example.servicedb.model.Person;
import com.example.servicedb.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
@Slf4j
@Tag(name = "Person API", description = "Endpoints for managing persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
    @Operation(summary = "Get all persons", description = "Retrieves a list of all persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of persons")
    })
    public List<Person> getAllPersons() {
        log.info("Fetching all persons");
        List<Person> persons = personService.getAllPersons();
        log.debug("Retrieved {} persons", persons.size());
        return persons;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID", description = "Retrieves a person by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved person"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Person> getPersonById(@Parameter(description = "ID of the person") @PathVariable Long id) {
        log.info("Fetching person with ID: {}", id);
        Optional<Person> person = personService.getPersonById(id);
        if (person.isPresent()) {
            log.debug("Found person: {}", person.get().getName());
            return ResponseEntity.ok(person.get());
        } else {
            log.warn("Person with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new person", description = "Creates a new person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person created successfully")
    })
    public Person createPerson(@RequestBody Person person) {
        log.info("Creating new person: {}", person.getName());
        Person createdPerson = personService.createPerson(person);
        log.debug("Created person with ID: {}", createdPerson.getId());
        return createdPerson;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a person", description = "Updates an existing person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Person> updatePerson(@Parameter(description = "ID of the person to update") @PathVariable Long id, @RequestBody Person person) {
        log.info("Updating person with ID: {}", id);
        Person updatedPerson = personService.updatePerson(id, person);
        log.debug("Updated person: {}", updatedPerson.getName());
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person", description = "Deletes a person by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Void> deletePerson(@Parameter(description = "ID of the person to delete") @PathVariable Long id) {
        log.info("Deleting person with ID: {}", id);
        personService.deletePerson(id);
        log.debug("Deleted person with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}