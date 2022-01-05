package com.example.patients.service.constraint;

import com.example.patients.service.validator.AddressNotTakenValidator;
import com.example.patients.service.validator.AddressValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressNotTakenValidator.class)
@Documented
public @interface ValidAddressNotTaken {

    String message() default "Address already taken!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
