package com.example.patients.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ConsultDto {

    private Long id;

    private Date date;

    private String diagnose;

    private String symptoms;

    private String comment;

    private DoctorDto doctor;

    private Long patientId;

    private List<MedicationDto> medications;
}
