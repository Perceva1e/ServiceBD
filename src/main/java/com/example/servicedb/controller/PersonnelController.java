package com.example.servicedb.controller;

import com.example.servicedb.model.Personnel;
import com.example.servicedb.service.PersonnelService;
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
@RequestMapping("/api/personnel")
@Slf4j
@Tag(name = "Personnel API", description = "Endpoints for managing personnel")
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

    @GetMapping
    @Operation(summary = "Get all personnel", description = "Retrieves a list of all personnel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of personnel")
    })
    public List<Personnel> getAllPersonnel() {
        log.info("Fetching all personnel");
        List<Personnel> personnelList = personnelService.getAllPersonnel();
        log.debug("Retrieved {} personnel entries", personnelList.size());
        return personnelList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get personnel by ID", description = "Retrieves a personnel entry by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved personnel"),
            @ApiResponse(responseCode = "404", description = "Personnel not found")
    })
    public ResponseEntity<Personnel> getPersonnelById(@Parameter(description = "ID of the personnel") @PathVariable Long id) {
        log.info("Fetching personnel with ID: {}", id);
        Optional<Personnel> personnel = personnelService.getPersonnelById(id);
        if (personnel.isPresent()) {
            log.debug("Found personnel with ID: {}", id);
            return ResponseEntity.ok(personnel.get());
        } else {
            log.warn("Personnel with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create new personnel", description = "Creates a new personnel entry")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnel created successfully")
    })
    public Personnel createPersonnel(@RequestBody Personnel personnel) {
        log.info("Creating new personnel");
        Personnel createdPersonnel = personnelService.createPersonnel(personnel);
        log.debug("Created personnel with ID: {}", createdPersonnel.getId());
        return createdPersonnel;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update personnel", description = "Updates an existing personnel entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnel updated successfully"),
            @ApiResponse(responseCode = "404", description = "Personnel not found")
    })
    public ResponseEntity<Personnel> updatePersonnel(@Parameter(description = "ID of the personnel to update") @PathVariable Long id, @RequestBody Personnel personnel) {
        log.info("Updating personnel with ID: {}", id);
        Personnel updatedPersonnel = personnelService.updatePersonnel(id, personnel);
        log.debug("Updated personnel with ID: {}", updatedPersonnel.getId());
        return ResponseEntity.ok(updatedPersonnel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete personnel", description = "Deletes a personnel entry by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Personnel deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Personnel not found")
    })
    public ResponseEntity<Void> deletePersonnel(@Parameter(description = "ID of the personnel to delete") @PathVariable Long id) {
        log.info("Deleting personnel with ID: {}", id);
        personnelService.deletePersonnel(id);
        log.debug("Deleted personnel with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}