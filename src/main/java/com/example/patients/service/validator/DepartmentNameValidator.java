package com.example.patients.service.validator;

import com.example.patients.service.DepartmentService;
import com.example.patients.service.constraint.ValidDepartmentName;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentNameValidator implements ConstraintValidator<ValidDepartmentName, String> {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentNameValidator(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public boolean isValid(String departmentName, ConstraintValidatorContext constraintValidatorContext) {
        return departmentService.getByName(departmentName) == null;
    }
}
