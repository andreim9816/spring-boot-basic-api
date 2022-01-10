package com.example.patients.constraint.annotation;

import com.example.patients.constraint.validator.UniqueDepartmentNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueDepartmentNameValidator.class)
@Documented
public @interface UniqueDepartmentName {

    String message() default "Department name already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
