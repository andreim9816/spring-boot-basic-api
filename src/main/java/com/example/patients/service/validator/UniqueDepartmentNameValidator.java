package com.example.patients.service.validator;

import com.example.patients.service.DepartmentService;
import com.example.patients.service.constraint.UniqueDepartmentName;
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
        return departmentService.getByName(departmentName) == null;
    }
}
