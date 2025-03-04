package com.example.demo.domain;

import com.example.demo.domain.validator.ValidEmployeePayload;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@ValidEmployeePayload
public record AddEmployeePayload(
    @NotNull(message = "employeeId is mandatory")
    long employeeId,

    @NotNull(message = "employeeType is mandatory")
    @Pattern(regexp = "FullTime|PartTime|Contractor", message = "Name must be one of the following: FullTime, PartTime, Contractor")
    String employeeType,

    @Max(value = 24, message = "max 24 hours")
    Integer workingHoursPerDay,

    BigDecimal baseSalary
){}