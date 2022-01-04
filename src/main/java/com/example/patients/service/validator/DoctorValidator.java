package com.example.patients.service.validator;

import com.example.patients.service.DoctorService;
import com.example.patients.service.constraint.ValidDoctor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoctorValidator implements ConstraintValidator<ValidDoctor, Long> {

    private final DoctorService doctorService;

    @Autowired
    public DoctorValidator(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public boolean isValid(Long doctorId, ConstraintValidatorContext constraintValidatorContext) {
        return doctorService.checkIfDoctorExists(doctorId);
    }
}
