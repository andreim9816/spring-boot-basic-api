package com.example.patients.service;

import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.model.Consult;
import com.example.patients.repository.ConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultService {

    private final ConsultRepository consultRepository;

    @Autowired
    public ConsultService(ConsultRepository consultRepository) {
        this.consultRepository = consultRepository;
    }

    public List<Consult> getAll() {
        return consultRepository.findAll();
    }

    public Consult getById(Long id) {
        return consultRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(id)
                        .entityType("Consult")
                        .build()
                );
    }

    public Boolean checkIfConsultExists(Long id) {
        return consultRepository.findById(id).isPresent();
    }

    public List<Consult> getAllConsultsForDoctorAndPatient(Long doctorId, Long patientId) {
        return consultRepository.getConsultsByDoctorIdAndPatientId(doctorId, patientId);
    }

    public Consult save(Consult department) {
        return consultRepository.save(department);
    }

    public void deleteById(Long id) {
        consultRepository.deleteById(id);
    }
}
