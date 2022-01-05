package com.example.patients.service.validator;

import com.example.patients.service.ConsultService;
import com.example.patients.service.MedicationService;
import com.example.patients.service.constraint.ValidConsult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsultValidator implements ConstraintValidator<ValidConsult, Long> {

    private final ConsultService consultService;

    @Autowired
    public ConsultValidator(ConsultService consultService) {
        this.consultService = consultService;
    }

    @Override
    public boolean isValid(Long consultId, ConstraintValidatorContext constraintValidatorContext) {
        return consultService.checkIfConsultExists(consultId);
    }
}
