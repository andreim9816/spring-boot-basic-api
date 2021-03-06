package com.example.patients.dto;

import lombok.Data;

@Data
public class PatientDto extends PersonDto {

    private Long id;

    private String cnp;

    private DepartmentDto department;

    private AddressDto address;
}
