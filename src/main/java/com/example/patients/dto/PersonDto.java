package com.example.patients.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PersonDto {

    public static final String NAME_REGEX = "^([a-zA-Z',.-]+( [a-zA-Z',.-]+)*)$";

    @Length(min = 3, message = "First name should have minimum 3 letters!")
    @Length(max = 30, message = "First name should have maximum 30 letters!")
    @Pattern(regexp = NAME_REGEX, message = "Invalid first name")
    @NotBlank
    private String firstName;

    @Length(min = 2, message = "Last name should have minimum 2 letters!")
    @Length(max = 30, message = "Last name should have maximum 30 letters!")
    @Pattern(regexp = NAME_REGEX, message = "Invalid last name")
    @NotBlank
    private String lastName;
}
