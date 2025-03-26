package com.example.demo.create.validator;

import com.example.demo.create.AddEmployeePayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class AddEmployeePayloadValidator implements ConstraintValidator<ValidAddEmployeePayload, AddEmployeePayload> {
    @Override
    public boolean isValid(AddEmployeePayload payload, ConstraintValidatorContext context) {
        if (payload.employeeType().equals("FullTime") || payload.employeeType().equals("Contractor")) {
            if (payload.baseSalary() == null || payload.baseSalary().compareTo(BigDecimal.ZERO) <= 0) {
                context.buildConstraintViolationWithTemplate("Base salary must be more than 0 for FullTime and Contractors")
                        .addPropertyNode("baseSalary")
                        .addConstraintViolation();
                return false;
            }
            if (payload.workingHoursPerDay() != null) {
                context.buildConstraintViolationWithTemplate("workingHoursPerDay must not be provided for FullTime and Contractors")
                        .addPropertyNode("workingHoursPerDay")
                        .addConstraintViolation();
                return false;
            }
        }

        if (payload.employeeType().equals("PartTime")) {
            if (payload.workingHoursPerDay() == null || payload.workingHoursPerDay() <= 0) {

                context.buildConstraintViolationWithTemplate("workingHoursPerDay must be more than 0 for PartTime")
                        .addPropertyNode("workingHoursPerDay")
                        .addConstraintViolation();
                return false;
            }
            if (payload.baseSalary() != null) {
                context.buildConstraintViolationWithTemplate("base salary must not be provided for PartTime")
                        .addPropertyNode("baseSalary")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
