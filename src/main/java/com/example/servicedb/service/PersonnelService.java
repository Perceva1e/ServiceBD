package com.example.servicedb.service;

import com.example.servicedb.model.Personnel;
import com.example.servicedb.repository.PersonnelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonnelService {
    private final PersonnelRepository personnelRepository;

    public List<Personnel> getAllPersonnel() {
        log.info("Fetching all personnel from repository");
        List<Personnel> personnelList = personnelRepository.findAll();
        log.debug("Retrieved {} personnel entries", personnelList.size());
        return personnelList;
    }

    public Optional<Personnel> getPersonnelById(Long id) {
        log.info("Fetching personnel with ID: {}", id);
        Optional<Personnel> personnel = personnelRepository.findById(id);
        if (personnel.isPresent()) {
            log.debug("Found personnel with position: {}", personnel.get().getPosition());
        } else {
            log.warn("Personnel with ID {} not found", id);
        }
        return personnel;
    }

    public Personnel createPersonnel(Personnel personnel) {
        log.info("Creating personnel with position: {}", personnel.getPosition());
        Personnel savedPersonnel = personnelRepository.save(personnel);
        log.debug("Created personnel with ID: {}", savedPersonnel.getId());
        return savedPersonnel;
    }

    public Personnel updatePersonnel(Long id, Personnel personnelDetails) {
        log.info("Updating personnel with ID: {}", id);
        Personnel personnel = personnelRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Personnel with ID {} not found for update", id);
                    return new RuntimeException("Personnel not found with ID: " + id);
                });
        personnel.setPosition(personnelDetails.getPosition());
        personnel.setFilm(personnelDetails.getFilm());
        personnel.setPerson(personnelDetails.getPerson());
        Personnel updatedPersonnel = personnelRepository.save(personnel);
        log.debug("Updated personnel with position: {}", updatedPersonnel.getPosition());
        return updatedPersonnel;
    }

    public void deletePersonnel(Long id) {
        log.info("Deleting personnel with ID: {}", id);
        if (!personnelRepository.existsById(id)) {
            log.warn("Personnel with ID {} not found for deletion", id);
            throw new RuntimeException("Personnel not found with ID: " + id);
        }
        personnelRepository.deleteById(id);
        log.debug("Deleted personnel with ID: {}", id);
    }
}