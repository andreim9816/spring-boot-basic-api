package com.example.patients.dto.input.patch;

import com.example.patients.dto.PersonDto;
import com.example.patients.service.constraint.ValidAddress;
import com.example.patients.service.constraint.ValidDepartmentId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.example.patients.dto.input.ReqPatientDto.CNP_REGEX;

@Data
public class ReqPatientDtoPatch extends PersonDto {

    @ValidDepartmentId
    private Long departmentId;

    @NotBlank
    @Pattern(regexp = CNP_REGEX, message = "Invalid CNP!")
    private String cnp;

    @NotNull
    @ValidAddress
    private Long addressId;
}
