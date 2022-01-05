package com.example.patients.dto.input.patch;

import com.example.patients.dto.PersonDto;
import com.example.patients.service.constraint.UniqueCnp;
import com.example.patients.service.constraint.ValidAddress;
import com.example.patients.service.constraint.ValidAddressNotTaken;
import com.example.patients.service.constraint.ValidDepartmentId;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.example.patients.dto.input.ReqPatientDto.CNP_REGEX;

@Data
public class ReqPatientDtoPatch extends PersonDto {

    @ValidDepartmentId
    private Long departmentId;

    @Pattern(regexp = CNP_REGEX, message = "Invalid CNP!")
    @UniqueCnp
    private String cnp;

    @ValidAddress
    @ValidAddressNotTaken
    private Long addressId;
}
