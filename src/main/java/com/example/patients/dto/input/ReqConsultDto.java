package com.example.patients.dto.input;

import com.example.patients.service.constraint.ValidDoctor;
import com.example.patients.service.constraint.ValidMedication;
import com.example.patients.service.constraint.ValidPatient;
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
