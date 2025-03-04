package com.example.demo.models.salary;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FullTimeSalaryTest {

    @Test
    void shouldGiveCorrectFullTimeSalary() {
        FullTimeSalary fullTimeSalary = new FullTimeSalary(new BigDecimal("10000"));

        assertThat(fullTimeSalary.computeAnnualSalary())
                .isEqualTo(new BigDecimal("12000").setScale(2, RoundingMode.HALF_UP));
    }
}