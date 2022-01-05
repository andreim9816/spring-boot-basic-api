package com.example.patients.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
public class PersonDto {

    private static final String REGEX = "^([a-zA-Z',.-]+( [a-zA-Z',.-]+)*)$";

    @Length(min = 3, message = "First name should have minimum 3 letters!")
    @Length(max = 30, message = "First name should have maximum 30 letters!")
    @Pattern(regexp = REGEX, message = "Invalid first name")
    private String firstName;

    @Length(min = 2, message = "Last name should have minimum 2 letters!")
    @Length(max = 30, message = "Last name should have maximum 30 letters!")
    @Pattern(regexp = REGEX, message = "Invalid last name")
    private String lastName;
}
