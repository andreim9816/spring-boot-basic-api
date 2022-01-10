package com.example.patients.dto.input;

import com.example.patients.constraint.annotation.UniqueCnp;
import com.example.patients.constraint.annotation.ValidAddress;
import com.example.patients.constraint.annotation.ValidAddressNotTaken;
import com.example.patients.dto.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
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
