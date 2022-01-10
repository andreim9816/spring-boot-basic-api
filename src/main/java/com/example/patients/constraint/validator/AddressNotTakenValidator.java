package com.example.patients.constraint.validator;

import com.example.patients.constraint.annotation.ValidAddressNotTaken;
import com.example.patients.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressNotTakenValidator implements ConstraintValidator<ValidAddressNotTaken, Long> {

    private final AddressService addressService;

    @Autowired
    public AddressNotTakenValidator(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public boolean isValid(Long addressId, ConstraintValidatorContext constraintValidatorContext) {
        return !addressService.checkIfAddressIsTakenByPatient(addressId);
    }
}
