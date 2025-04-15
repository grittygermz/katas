package com.example.demo.employee.create.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreateEmployeePayloadValidator.class)
public @interface ValidCreateEmployeePayload {
    String message() default "Invalid create employee payload";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
