package com.example.patients.dto.input;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ReqAddressDto {

    public static final String STREET_REGEX = "^([a-zA-Z',.-]+( [a-zA-Z',.-]+)*)$";
    public static final String CITY_REGEX = "^([a-zA-Z',.-]+( [a-zA-Z',.-]+)*)$";

    @Length(min = 3, message = "Street name should have minimum 3 letters!")
    @Length(max = 30, message = "Street name should have maximum 30 letters!")
    @Pattern(regexp = STREET_REGEX, message = "Invalid street name")
    @NotBlank(message = "Street name must be provided!")
    private String street;

    @NotNull(message = "Number should be provided!")
    @Min(value = 1, message = "Number should pe positive!")
    private Integer number;

    @Length(min = 3, message = "City name should have minimum 3 letters!")
    @Length(max = 30, message = "City name should have maximum 30 letters!")
    @Pattern(regexp = CITY_REGEX, message = "Invalid city name")
    @NotBlank(message = "City name must be provided!")
    private String city;
}
