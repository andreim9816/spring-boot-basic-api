package com.example.patients.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.example.patients.dto.input.ReqAddressDto.CITY_REGEX;
import static com.example.patients.dto.input.ReqAddressDto.STREET_REGEX;

@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long id;

    @Length(min = 3, message = "Street name should have minimum 3 letters!")
    @Length(max = 30, message = "Street name should have maximum 30 letters!")
    @Pattern(regexp = STREET_REGEX, message = "Invalid street name")
    private String street;

    @NotNull(message = "Number should be provided!")
    @Min(value = 1, message = "Number should pe positive!")
    @Column(name = "NUMBER")
    private Integer no;

    @Length(min = 3, message = "City name should have minimum 3 letters!")
    @Length(max = 30, message = "City name should have maximum 30 letters!")
    @Pattern(regexp = CITY_REGEX, message = "Invalid city name")
    private String city;

    @OneToOne(mappedBy = "address")
    @ToString.Exclude
    private Patient patient;
}
