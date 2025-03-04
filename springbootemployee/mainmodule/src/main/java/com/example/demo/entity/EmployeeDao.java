package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Table("Employee")
public class EmployeeDao {
    @Id
    private long id;
    @Column("employeeId")
    private long employeeId;
    @Column("employeeType")
    private String employeeType;
    @Column("workingHoursPerDay")
    private Integer workingHoursPerDay;
    @Column("baseSalary")
    private BigDecimal baseSalary;

    public EmployeeDao(long employeeId, String employeeType, Integer workingHoursPerDay, BigDecimal baseSalary) {
        this.employeeId = employeeId;
        this.employeeType = employeeType;
        this.workingHoursPerDay = workingHoursPerDay;
        this.baseSalary = baseSalary;
    }

    public EmployeeDao() {
    }
}
