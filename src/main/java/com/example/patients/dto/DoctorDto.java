package com.example.patients.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDto extends PersonDto {

    private Long id;

    private DepartmentDto department;
}
