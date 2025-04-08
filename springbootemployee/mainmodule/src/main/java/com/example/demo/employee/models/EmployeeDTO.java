package com.example.demo.employee.models;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class EmployeeDTO {
    private final long employeeId;
    private final String employeeType;
    private final Integer workingHoursPerDay;
    private BigDecimal baseSalary;
    private final BigDecimal totalAnnualSalary;
    private final int stockCount;

    public EmployeeDTO(long employeeId, String employeeType, Integer workingHoursPerDay, BigDecimal baseSalary, BigDecimal totalAnnualSalary, int stockCount) {
        this.employeeId = employeeId;
        this.employeeType = employeeType;
        this.workingHoursPerDay = workingHoursPerDay;
        if(baseSalary != null) {
            this.baseSalary = baseSalary.setScale(2, RoundingMode.HALF_UP);
        }
        this.totalAnnualSalary = totalAnnualSalary.setScale(2, RoundingMode.HALF_UP);
        this.stockCount = stockCount;
    }

    public EmployeeDTO(long employeeId, String employeeType, Integer workingHoursPerDay, BigDecimal totalAnnualSalary, int stockCount) {
        this(employeeId, employeeType, workingHoursPerDay, null, totalAnnualSalary, stockCount);
    }
}
