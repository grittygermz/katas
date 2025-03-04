package com.example.demo.models.salary;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PartTimeSalaryTest {

    @Test
    void shouldGiveCorrectSalary() {
        PartTimeSalary partTimeSalary = new PartTimeSalary(12);

        assertThat(partTimeSalary.computeAnnualSalary())
                .isEqualTo(new BigDecimal("156000").setScale(2, RoundingMode.HALF_UP));
    }

}