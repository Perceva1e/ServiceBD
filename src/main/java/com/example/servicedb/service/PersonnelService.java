package com.example.servicedb.service;

import com.example.servicedb.model.Personnel;
import com.example.servicedb.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelService {
    @Autowired
    private PersonnelRepository personnelRepository;

    public List<Personnel> getAllPersonnel() { return personnelRepository.findAll(); }
    public Optional<Personnel> getPersonnelById(Long id) { return personnelRepository.findById(id); }
    public Personnel createPersonnel(Personnel personnel) { return personnelRepository.save(personnel); }
    public Personnel updatePersonnel(Long id, Personnel personnelDetails) {
        Personnel personnel = personnelRepository.findById(id).orElseThrow();
        personnel.setPosition(personnelDetails.getPosition());
        personnel.setFilm(personnelDetails.getFilm());
        personnel.setPerson(personnelDetails.getPerson());
        return personnelRepository.save(personnel);
    }
    public void deletePersonnel(Long id) { personnelRepository.deleteById(id); }
}