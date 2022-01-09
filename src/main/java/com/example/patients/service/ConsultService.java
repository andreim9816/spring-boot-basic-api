package com.example.patients.service;

import com.example.patients.dto.input.ReqConsultDto;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.ConsultMapper;
import com.example.patients.model.Consult;
import com.example.patients.repository.ConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultService {

    private final ConsultRepository consultRepository;
    private final ConsultMapper consultMapper;

    @Autowired
    public ConsultService(ConsultRepository consultRepository, ConsultMapper consultMapper) {
        this.consultRepository = consultRepository;
        this.consultMapper = consultMapper;
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

    public Consult updateConsult(ReqConsultDto reqConsultDto, Consult consult) {
        Consult upadatedConsult = consultMapper.update(reqConsultDto, consult);

        return saveConsult(upadatedConsult);
    }

    public Consult saveConsult(Consult department) {
        return consultRepository.save(department);
    }

    public void deleteConsultById(Long id) {
        consultRepository.deleteById(id);
    }
}
