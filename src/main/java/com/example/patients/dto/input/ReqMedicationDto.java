package com.example.patients.dto.input;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ReqMedicationDto {

    @NotBlank(message = "Medication name must be provided!")
    private String name;

    @NotBlank(message = "Quantity must be provided!")
    @Min(value = 1, message = "Quantity must be positive!")
    private Integer quantity;
}
