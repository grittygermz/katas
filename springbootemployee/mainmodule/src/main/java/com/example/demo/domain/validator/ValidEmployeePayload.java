package com.example.demo.domain.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeePayloadValidator.class)
public @interface ValidEmployeePayload {
    String message() default "Invalid employee payload";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
