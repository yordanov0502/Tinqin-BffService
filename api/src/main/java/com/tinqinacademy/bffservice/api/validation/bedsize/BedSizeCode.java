package com.tinqinacademy.bffservice.api.validation.bedsize;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD,TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BedSizeCodeValidation.class)
public @interface BedSizeCode {
    String message() default "UNKNOWN bed size.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
