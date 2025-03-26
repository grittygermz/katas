package com.example.demo.update.validator;

import com.example.demo.models.employee.EmployeeType;
import com.example.demo.update.PutEmployeePayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PutEmployeePayloadValidator implements ConstraintValidator<ValidPutEmployeePayload, PutEmployeePayload> {
    @Override
    public boolean isValid(PutEmployeePayload payload, ConstraintValidatorContext constraintValidatorContext) {
        if(payload.employeeType().equals(EmployeeType.PARTTIMEEMPLOYEE.getValue())) {
            if(payload.workingHoursPerDay() == null) {
                constraintValidatorContext.buildConstraintViolationWithTemplate("partTime employee should have value for workingHoursPerDay")
                        .addPropertyNode("workingHoursPerDay")
                        .addConstraintViolation();
                return false;
            }
        } else {
            if(payload.baseSalary() == null) {
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("fullTime or contractor employee should have value for baseSalary")
                        .addPropertyNode("baseSalary")
                        .addConstraintViolation();
                return false;
            }
            //return payload.baseSalary() != null;
        }
        return true;
    }
}
