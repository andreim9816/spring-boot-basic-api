package com.example.patients.service.validator;

import com.example.patients.service.MedicationService;
import com.example.patients.service.constraint.ValidMedication;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MedicationValidator implements ConstraintValidator<ValidMedication, Long> {

    private final MedicationService medicationService;

    @Autowired
    public MedicationValidator(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @Override
    public boolean isValid(Long medicationId, ConstraintValidatorContext constraintValidatorContext) {
        return medicationService.checkIfMedicationExists(medicationId);
    }
}
