package com.example.demo.models.employee;

import com.example.demo.models.salary.FullTimeSalary;
import com.example.demo.models.salary.Salary;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FullTimeEmployee implements Employee {
    private final long employeeId;
    private final Salary salary;
    private final int stocks;

    public FullTimeEmployee(long employeeId, BigDecimal baseSalary, int stocks) {
        this.employeeId = employeeId;
        this.salary = new FullTimeSalary(baseSalary);
        this.stocks = stocks;
    }

    @Override
    public BigDecimal getAnnualSalary() {
        return salary.computeAnnualSalary();
    }
}
