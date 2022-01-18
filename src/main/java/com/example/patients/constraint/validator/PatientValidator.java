package com.example.patients.constraint.validator;

import com.example.patients.constraint.annotation.ValidPatient;
import com.example.patients.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatientValidator implements ConstraintValidator<ValidPatient, Long> {

    private final PatientService patientService;

    @Autowired
    public PatientValidator(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public boolean isValid(Long patientId, ConstraintValidatorContext constraintValidatorContext) {
        if (patientId == null) {
            return false;
        }
        return patientService.checkIfPatientExists(patientId);
    }
}
