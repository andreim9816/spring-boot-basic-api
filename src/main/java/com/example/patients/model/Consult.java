package com.example.patients.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONSULT_ID")
    private Long id;

    private Date date;

    private String diagnose;

    private String symptoms;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "FK_DOCTOR_ID")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "FK_PATIENT_ID")
    private Patient patient;

    @ManyToMany
    @JoinTable(name = "Prescription",
            joinColumns = @JoinColumn(name = "CONSULT_ID"),
            inverseJoinColumns = @JoinColumn(name = "MEDICATION_ID"))
    private List<Medication> medications;
}
