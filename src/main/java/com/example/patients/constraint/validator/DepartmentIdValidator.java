package com.example.patients.constraint.validator;

import com.example.patients.constraint.annotation.ValidDepartmentId;
import com.example.patients.service.DepartmentService;
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
        if (departmentId == null) {
            return false;
        }
        return departmentService.checkIfDepartmentExists(departmentId);
    }
}
