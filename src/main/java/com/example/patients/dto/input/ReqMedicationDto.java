package com.example.patients.dto.input;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ReqMedicationDto {

    private String name;

    @Min(value = 1, message = "Quantity must be positive!")
    private Integer quantity;
}
