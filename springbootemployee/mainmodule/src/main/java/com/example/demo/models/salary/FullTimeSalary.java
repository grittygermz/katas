package com.example.demo.models.salary;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record FullTimeSalary(BigDecimal baseSalary) implements Salary {
    @Override
    public BigDecimal computeAnnualSalary() {
        double bonus = 0.20;
        return baseSalary.multiply(new BigDecimal(1 + bonus))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
