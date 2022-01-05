package com.example.patients.service.validator;

import com.example.patients.service.PatientService;
import com.example.patients.service.constraint.ValidPatient;
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
        return patientService.checkIfPatientExists(patientId);
    }
}
