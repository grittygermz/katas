package com.example.demo.models.employee;

import com.example.demo.models.salary.ContractorSalary;
import com.example.demo.models.salary.Salary;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class ContractorEmployee implements Employee{
    private final long employeeId;
    private final Salary salary;

    public ContractorEmployee(long employeeId, BigDecimal baseSalary) {
        this.employeeId = employeeId;
        this.salary = new ContractorSalary(baseSalary.setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public BigDecimal getAnnualSalary() {
        return salary.computeAnnualSalary();
    }
}
