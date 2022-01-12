package com.example.patients.dto.input.update;

import com.example.patients.constraint.annotation.ValidAddress;
import com.example.patients.constraint.annotation.ValidDepartmentId;
import com.example.patients.dto.PersonDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.example.patients.dto.input.ReqPatientDto.CNP_REGEX;

@Data
public class ReqPatientUpdateDto extends PersonDto {

    @ValidDepartmentId
    private Long departmentId;

    @NotBlank
    @Pattern(regexp = CNP_REGEX, message = "Invalid CNP!")
    private String cnp;

    @NotNull
    @ValidAddress
    private Long addressId;
}
