package com.example.patients.dto;

import lombok.Data;

@Data
public class DoctorDto extends PersonDto {

    private Long id;

    private DepartmentDto department;
}
