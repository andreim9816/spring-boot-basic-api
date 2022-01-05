package com.example.patients.service;

import com.example.patients.model.Patient;
import com.example.patients.repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Boolean checkIfPatientExists(Long id) {
        return patientRepository.findById(id).isPresent();
    }

    public Boolean checkIfCnpExists(String cnp) {
        return patientRepository.checkIfAddressIsTakenByPatient(cnp);
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}
