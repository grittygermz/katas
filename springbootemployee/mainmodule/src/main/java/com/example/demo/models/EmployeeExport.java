package com.example.demo.models;

import java.math.BigDecimal;

public record EmployeeExport(long employeeId, String employeeType, Integer workingHoursPerDay, BigDecimal baseSalary, BigDecimal totalAnnualSalary, int stockCount) {
}
