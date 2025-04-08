package com.example.demo.employee.create.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddEmployeePayloadValidator.class)
public @interface ValidAddEmployeePayload {
    String message() default "Invalid add employee payload";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
