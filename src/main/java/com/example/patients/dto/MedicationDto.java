package com.example.patients.dto;

import lombok.Data;

@Data
public class MedicationDto {

    private Long id;

    private String name;

    private Integer quantity;
}
