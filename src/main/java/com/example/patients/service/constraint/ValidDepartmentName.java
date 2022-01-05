package com.example.patients.service.constraint;

import com.example.patients.service.validator.DepartmentNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DepartmentNameValidator.class)
@Documented
public @interface ValidDepartmentName {

    String message() default "Invalid department name!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
