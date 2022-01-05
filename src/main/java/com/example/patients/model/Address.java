package com.example.patients.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long id;

    private String street;

    @Column(name = "NUMBER")
    private Integer no;

    private String city;

    @OneToOne(mappedBy = "address")
    @ToString.Exclude
    private Patient patient;
}
