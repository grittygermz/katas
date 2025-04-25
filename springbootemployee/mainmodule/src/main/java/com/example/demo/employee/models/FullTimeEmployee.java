package com.example.demo.employee.models;

import com.example.demo.employee.models.salary.FullTimeSalary;
import com.example.demo.employee.models.salary.Salary;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class FullTimeEmployee implements Employee {
    private final long employeeId;
    private final Salary salary;
    private final int stocks;

    public FullTimeEmployee(long employeeId, BigDecimal baseSalary, int stocks) {
        this.employeeId = employeeId;
        this.salary = new FullTimeSalary(baseSalary.setScale(2, RoundingMode.HALF_UP));
        this.stocks = stocks;
    }

    @Override
    public BigDecimal getAnnualSalary() {
        return salary.computeAnnualSalary();
    }

    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.FULLTIMEEMPLOYEE;
    }
}
