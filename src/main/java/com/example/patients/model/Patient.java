package com.example.patients.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Patient extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    private Long id;

    private String cnp;

    @ManyToOne
    @JoinColumn(name = "FK_DEPARTMENT_ID")
    private Department department;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Consult> consults;

    @OneToOne(mappedBy = "patient")
    private Address address;
}
