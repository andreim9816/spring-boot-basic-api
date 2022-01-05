package com.example.patients.dto.input;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ReqMedicationDto {

    @NotNull(message = "Medication name must be provided!")
    private String name;

    @NotNull(message = "Quantity must be provided!")
    @Min(value = 1, message = "Quantity must be positive!")
    private Integer quantity;
}
