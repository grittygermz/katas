package com.example.demo.employee.models.salary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ContractorSalary implements Salary {

    private final BigDecimal baseSalary;

    public ContractorSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Override
    public BigDecimal computeAnnualSalary() {
        return baseSalary.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractorSalary that = (ContractorSalary) o;
        return Objects.equals(baseSalary, that.baseSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(baseSalary);
    }
}
