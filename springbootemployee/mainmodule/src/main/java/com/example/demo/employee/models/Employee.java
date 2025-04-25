package com.example.demo.employee.models;

import java.math.BigDecimal;

public interface Employee {
    BigDecimal getAnnualSalary();
    EmployeeType getEmployeeType();
}
