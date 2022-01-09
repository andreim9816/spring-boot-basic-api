package com.example.patients.service;

import com.example.patients.dto.input.patch.ReqDoctorDtoPatch;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.DoctorMapper;
import com.example.patients.model.Doctor;
import com.example.patients.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(id)
                        .entityType("Doctor")
                        .build()
                );
    }

    public Boolean checkIfDoctorExists(Long id) {
        return doctorRepository.findById(id).isPresent();
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(ReqDoctorDtoPatch reqDoctorDtoPatch, Doctor doctor) {
        Doctor updatedDoctor = doctorMapper.update(reqDoctorDtoPatch, doctor);

        return saveDoctor(updatedDoctor);
    }

    public void deleteDoctorById(Long id) {
        doctorRepository.deleteById(id);
    }
}
