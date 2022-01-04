package com.example.patients.dto;

import lombok.Data;

@Data
public class AddressDto {

    private Long id;

    private String street;

    private Integer number;

    private String city;
}
