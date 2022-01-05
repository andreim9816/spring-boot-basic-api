package com.example.patients.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEDICATION_ID")
    private Long id;

    @NotBlank(message = "Medication name must be provided!")
    private String name;

    @NotBlank(message = "Quantity must be provided!")
    @Min(value = 1, message = "Quantity must be positive!")
    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "Prescription",
            joinColumns = @JoinColumn(name = "MEDICATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CONSULT_ID"))
    @ToString.Exclude
    private List<Consult> consults;
}
