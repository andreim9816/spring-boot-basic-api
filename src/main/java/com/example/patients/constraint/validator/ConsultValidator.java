package com.example.patients.constraint.validator;

import com.example.patients.constraint.annotation.ValidConsult;
import com.example.patients.service.ConsultService;
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
