package com.example.patients.repository;

import com.example.patients.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT CASE WHEN (COUNT(pat) > 0) THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM Patient pat WHERE pat.cnp = :cnp")
    Boolean checkIfAddressIsTakenByPatient(@Param("cnp") String cnp);
}
