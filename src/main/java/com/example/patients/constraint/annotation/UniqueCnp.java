package com.example.patients.constraint.annotation;

import com.example.patients.constraint.validator.UniqueCnpValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCnpValidator.class)
@Documented
public @interface UniqueCnp {
    String message() default "CNP already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
