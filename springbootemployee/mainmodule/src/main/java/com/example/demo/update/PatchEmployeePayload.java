package com.example.demo.update;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record PatchEmployeePayload(
        @Pattern(regexp = "FullTime|PartTime|Contractor", message = "Name must be one of the following: FullTime, PartTime, Contractor")
        String employeeType,

        @Max(value = 24, message = "max 24 hours")
        @DecimalMin(value = "1")
        Integer workingHoursPerDay,

        @DecimalMin(value = "0.01")
        BigDecimal baseSalary
) {
}
