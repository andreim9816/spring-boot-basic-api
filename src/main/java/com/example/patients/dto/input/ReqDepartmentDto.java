package com.example.patients.dto.input;

import com.example.patients.constraint.annotation.UniqueDepartmentName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.patients.dto.PersonDto.NAME_REGEX;

@Data
public class ReqDepartmentDto {

    @UniqueDepartmentName
    @NotBlank(message = "Department name must be provided!")
    @Pattern(regexp = NAME_REGEX, message = "Invalid department name")
    private String name;
}
