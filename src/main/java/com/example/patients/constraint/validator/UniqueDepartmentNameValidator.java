package com.example.patients.constraint.validator;

import com.example.patients.constraint.annotation.UniqueDepartmentName;
import com.example.patients.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueDepartmentNameValidator implements ConstraintValidator<UniqueDepartmentName, String> {

    private final DepartmentService departmentService;

    @Autowired
    public UniqueDepartmentNameValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public boolean isValid(String departmentName, ConstraintValidatorContext constraintValidatorContext) {
        if (departmentName == null) {
            return false;
        }
        return departmentService.getDepartmentByName(departmentName).isEmpty();
    }
}
