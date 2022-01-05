package com.example.patients.dto.input;

import com.example.patients.dto.PersonDto;
import com.example.patients.service.constraint.UniqueCnp;
import com.example.patients.service.constraint.ValidAddress;
import com.example.patients.service.constraint.ValidAddressNotTaken;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ReqPatientDto extends PersonDto {

    public static final String CNP_REGEX = "^[1-9][0-9]{12}$";

    @Pattern(regexp = CNP_REGEX, message = "Invalid CNP!")
    @NotBlank(message = "CNP must be provided!")
    @UniqueCnp
    private String cnp;

    @ValidAddress
    @ValidAddressNotTaken
    private Long addressId;
}
