package com.example.patients.service.validator;

import com.example.patients.service.AddressService;
import com.example.patients.service.constraint.ValidAddress;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<ValidAddress, Long> {

    private final AddressService addressService;

    @Autowired
    public AddressValidator(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public boolean isValid(Long addressId, ConstraintValidatorContext constraintValidatorContext) {
        return addressService.checkIfAddressExists(addressId);
    }
}
