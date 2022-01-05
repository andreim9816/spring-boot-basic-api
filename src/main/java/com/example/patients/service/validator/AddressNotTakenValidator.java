package com.example.patients.service.validator;

import com.example.patients.service.AddressService;
import com.example.patients.service.constraint.ValidAddressNotTaken;
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
