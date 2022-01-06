package com.example.patients.service;

import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.model.Medication;
import com.example.patients.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }

    public Medication getById(Long id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(id)
                        .entityType("Medication")
                        .build()
                );
    }

    public Boolean checkIfMedicationExists(Long id) {
        return medicationRepository.findById(id).isPresent();
    }

    public Medication save(Medication doctor) {
        return medicationRepository.save(doctor);
    }

    public void deleteById(Long id) {
        medicationRepository.deleteById(id);
    }
}
