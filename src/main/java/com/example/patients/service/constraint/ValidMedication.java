package com.example.patients.service.constraint;

import com.example.patients.service.validator.MedicationValidator;
import com.example.patients.service.validator.PatientValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MedicationValidator.class)
@Documented
public @interface ValidMedication {

    String message() default "Invalid medication ID!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
