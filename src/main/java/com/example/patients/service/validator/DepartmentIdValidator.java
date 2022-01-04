package com.example.patients.service.validator;

import com.example.patients.service.DepartmentService;
import com.example.patients.service.constraint.ValidDepartmentId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentIdValidator implements ConstraintValidator<ValidDepartmentId, Long> {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentIdValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public boolean isValid(Long departmentId, ConstraintValidatorContext constraintValidatorContext) {
        return departmentService.checkIfDepartmentExists(departmentId);
    }
}
