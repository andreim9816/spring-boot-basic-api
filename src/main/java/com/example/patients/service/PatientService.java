package com.example.patients.service;

import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.model.Consult;
import com.example.patients.model.Medication;
import com.example.patients.model.Patient;
import com.example.patients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final MedicationService medicationService;

    @Autowired
    public PatientService(PatientRepository patientRepository, MedicationService medicationService) {
        this.patientRepository = patientRepository;
        this.medicationService = medicationService;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(patientId)
                        .entityType("Patient")
                        .build()
                );
    }

    public Boolean checkIfPatientExists(Long patientId) {
        return patientRepository.findById(patientId).isPresent();
    }

    public Boolean checkIfCnpExists(String cnp) {
        return patientRepository.existsPatientByCnp(cnp);
    }

    public List<Consult> getConsultsForPatient(Long patientId) {
        return getById(patientId).getConsults();
    }

    public Patient getLongestHospitalizedPatient() {
        return getAll().stream()
                .map(patient -> getConsultsForPatient(patient.getId()))
                .flatMap(Collection::stream)
                .min(Comparator.comparing(Consult::getDate))
                .map(Consult::getPatient)
                .orElse(null);
    }

    public List<Medication> getUniqueMedicationsInConsults(Long patientId) {
        return getConsultsForPatient(patientId).stream()
                .map(Consult::getMedications)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deleteById(Long patientId) {
        patientRepository.deleteById(patientId);
    }
}
