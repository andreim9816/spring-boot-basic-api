package com.example.patients.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class Person {

    private String firstName;

    private String lastName;
}
