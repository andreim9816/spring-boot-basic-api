package com.example.patients.service;

import com.example.patients.dto.input.ReqMedicationDto;
import com.example.patients.exception.CustomException;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.MedicationMapper;
import com.example.patients.model.Medication;
import com.example.patients.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public Medication getMedicationById(Long id) {
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

    public Medication saveMedication(Medication medication) {
        checkIfMedicationAlreadyExistsByNameAndQuantity(medication.getName(), medication.getQuantity());
        return medicationRepository.save(medication);
    }

    public Medication updateMedication(ReqMedicationDto reqMedicationDto, Medication medication) {
        Medication updatedMedication = medicationMapper.update(reqMedicationDto, medication);

        return saveMedication(updatedMedication);
    }

    public void deleteMedicationById(Long id) {
        medicationRepository.deleteById(id);
    }

    private void checkIfMedicationAlreadyExistsByNameAndQuantity(String name, Integer quantity) {
        if (medicationRepository.findMedicationByNameAndQuantity(name, quantity) != null) {
            throw new CustomException(String.format("Medication %s with quantity %s already exists!", name, quantity));
        }
    }
}
