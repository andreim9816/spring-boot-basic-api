package com.example.patients.repository;

import com.example.patients.model.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {

    List<Consult> getConsultsByDoctorIdAndPatientId(Long doctorId, Long patientId);
}
