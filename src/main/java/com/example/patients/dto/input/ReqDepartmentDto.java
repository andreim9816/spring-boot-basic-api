package com.example.patients.dto.input;

import com.example.patients.service.constraint.ValidDepartmentName;
import lombok.Data;

@Data
public class ReqDepartmentDto {

    @ValidDepartmentName
    private String name;
}
