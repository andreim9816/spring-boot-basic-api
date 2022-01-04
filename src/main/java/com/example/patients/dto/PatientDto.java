package com.example.patients.dto;

import lombok.Data;

import java.util.List;

@Data
public class PatientDto extends PersonDto {

    private Long id;

    private String cnp;

    private DepartmentDto department;

    private List<ConsultDto> consults;

    private AddressDto address;
}
