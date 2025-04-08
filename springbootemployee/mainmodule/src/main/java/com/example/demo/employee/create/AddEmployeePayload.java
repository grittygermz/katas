package com.example.demo.employee.create;

import com.example.demo.employee.create.validator.ValidAddEmployeePayload;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@ValidAddEmployeePayload
public record AddEmployeePayload(
    @NotNull(message = "employeeId is mandatory")
    Long employeeId,

    @NotNull(message = "employeeType is mandatory")
    @Pattern(regexp = "FullTime|PartTime|Contractor", message = "Name must be one of the following: FullTime, PartTime, Contractor")
    String employeeType,

    @Max(value = 24, message = "max 24 hours")
    //@DecimalMin(value = "1")
    Integer workingHoursPerDay,

    //@DecimalMin(value = "0.01")
    BigDecimal baseSalary
){}