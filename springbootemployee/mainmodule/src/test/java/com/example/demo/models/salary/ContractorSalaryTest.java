package com.example.demo.models.salary;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContractorSalaryTest {
    @Test
    void shouldGiveCorrectFullTimeSalary() {
        ContractorSalary contractorSalary = new ContractorSalary(new BigDecimal("10000"));

        assertThat(contractorSalary.computeAnnualSalary())
                .isEqualTo(new BigDecimal("10000").setScale(2, RoundingMode.HALF_UP));
    }

}