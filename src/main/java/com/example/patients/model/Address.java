package com.example.patients.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ADDRESS_ID")
    private Long id;

    private String street;

    @Column(name = "NUMBER")
    private Integer no;

    private String city;

    @OneToOne
    @JoinColumn(name = "FK_PATIENT_ID")
    private Patient patient;
}
