package com.example.demo.employee.models.salary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class FullTimeSalary implements Salary {
    private final BigDecimal baseSalary;
    private static final double DEFAULT_FULL_TIME_EMPLOYEE_BONUS = 0.20;

    public FullTimeSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Override
    public BigDecimal computeAnnualSalary() {
        return baseSalary.multiply(new BigDecimal(1 + DEFAULT_FULL_TIME_EMPLOYEE_BONUS))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullTimeSalary that = (FullTimeSalary) o;
        return Objects.equals(baseSalary, that.baseSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(baseSalary);
    }
}
