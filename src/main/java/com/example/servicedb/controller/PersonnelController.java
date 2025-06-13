package com.example.servicedb.controller;

import com.example.servicedb.model.Personnel;
import com.example.servicedb.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personnel")
public class PersonnelController {
    @Autowired
    private PersonnelService personnelService;

    @GetMapping
    public List<Personnel> getAllPersonnel() { return personnelService.getAllPersonnel(); }
    @GetMapping("/{id}")
    public ResponseEntity<Personnel> getPersonnelById(@PathVariable Long id) {
        Optional<Personnel> personnel = personnelService.getPersonnelById(id);
        return personnel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public Personnel createPersonnel(@RequestBody Personnel personnel) { return personnelService.createPersonnel(personnel); }
    @PutMapping("/{id}")
    public ResponseEntity<Personnel> updatePersonnel(@PathVariable Long id, @RequestBody Personnel personnel) {
        return ResponseEntity.ok(personnelService.updatePersonnel(id, personnel));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
        return ResponseEntity.noContent().build();
    }
}