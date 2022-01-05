package com.example.patients.dto.input;

import com.example.patients.service.constraint.UniqueDepartmentName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqDepartmentDto {

    @UniqueDepartmentName
    @NotBlank(message = "Department name must be provided!")
    private String name;
}
