package com.example.patients.service;

import com.example.patients.model.Doctor;
import com.example.patients.model.Patient;
import com.example.patients.repository.PatientRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public boolean delete(Long id) {
        patientRepository.deleteById(id);
        return true;
    }
}
