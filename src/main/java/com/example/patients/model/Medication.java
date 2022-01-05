package com.example.patients.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEDICATION_ID")
    private Long id;

    private String name;

    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "Prescription",
            joinColumns = @JoinColumn(name = "MEDICATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONSULT_ID"))
    @ToString.Exclude
    private List<Consult> consults;
}
