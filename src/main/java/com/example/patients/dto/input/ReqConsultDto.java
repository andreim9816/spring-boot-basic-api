package com.example.patients.dto.input;

import com.example.patients.constraint.annotation.ValidDoctor;
import com.example.patients.constraint.annotation.ValidMedication;
import com.example.patients.constraint.annotation.ValidPatient;
import lombok.Data;

import java.util.List;

@Data
public class ReqConsultDto {

    private String diagnose;

    private String symptoms;

    private String comment;

    @ValidDoctor
    private Long doctorId;

    @ValidPatient
    private Long patientId;

    private List<@ValidMedication Long> medicationIds;
}
