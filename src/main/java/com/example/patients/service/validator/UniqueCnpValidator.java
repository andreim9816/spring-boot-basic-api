package com.example.patients.service.validator;

import com.example.patients.service.PatientService;
import com.example.patients.service.constraint.UniqueCnp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueCnpValidator implements ConstraintValidator<UniqueCnp, String> {
    private final PatientService patientService;

    @Autowired
    public UniqueCnpValidator(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public boolean isValid(String cnp, ConstraintValidatorContext constraintValidatorContext) {
        return !patientService.checkIfCnpExists(cnp);

    }
}
