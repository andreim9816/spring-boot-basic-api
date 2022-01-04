package com.example.patients.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
public class PersonDto {

    @Length(min = 3, message = "First name should have minimum 3 letters!")
    @Length(max = 30, message = "First name is too long!")
    @Pattern(regexp = "^([a-zA-Z',.-]+( [a-zA-Z',.-]+)*)$", message = "Invalid first name")
    private String firstName;

    @Length(min = 2, message = "Last name should have minimum 2 letters!")
    @Length(max = 30, message = "Last name is too long!")
    @Pattern(regexp = "^([a-zA-Z',.-]+( [a-zA-Z',.-]+)*)$", message = "Invalid last name")
    private String lastName;
}
