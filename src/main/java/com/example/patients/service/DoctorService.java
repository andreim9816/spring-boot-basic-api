package com.example.patients.service;

import com.example.patients.model.Doctor;
import com.example.patients.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Boolean checkIfDoctorExists(Long id) {
        return doctorRepository.findById(id).isPresent();
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }
}
