package com.example.patients.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    @ToString.Exclude
    private Department department;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Consult> consults;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_ADDRESS_ID")
    @ToString.Exclude
    private Address address;
}
