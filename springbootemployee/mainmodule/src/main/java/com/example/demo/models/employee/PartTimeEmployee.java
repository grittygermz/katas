package com.example.demo.models.employee;

import com.example.demo.models.salary.PartTimeSalary;
import com.example.demo.models.salary.Salary;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartTimeEmployee implements Employee {

    private final long employeeId;
    private final Salary salary;
    private final int workingHoursPerDay;

    public PartTimeEmployee(long employeeId, int workingHoursPerDay) {
        this.employeeId = employeeId;
        this.workingHoursPerDay = workingHoursPerDay;
        this.salary = new PartTimeSalary(this.workingHoursPerDay);
    }

    @Override
    public BigDecimal getAnnualSalary() {
        return salary.computeAnnualSalary();
    }
}
