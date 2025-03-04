package com.example.demo.models.salary;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ContractorSalary(BigDecimal baseSalary) implements Salary {
    @Override
    public BigDecimal computeAnnualSalary() {
        return baseSalary.setScale(2, RoundingMode.HALF_UP);
    }
}
