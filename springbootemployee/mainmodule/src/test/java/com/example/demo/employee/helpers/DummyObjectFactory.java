package com.example.demo.employee.helpers;

import com.example.demo.employee.EmployeeDao;
import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.employee.*;
import com.example.demo.employee.models.salary.ContractorSalary;
import com.example.demo.employee.models.salary.FullTimeSalary;
import com.example.demo.employee.models.salary.PartTimeSalary;

import java.math.BigDecimal;

public class DummyObjectFactory {
    public static EmployeeDao getEmployeeDaoWithType(EmployeeType employeeType) {
        return switch (employeeType) {
            case CONTRACTOR -> new EmployeeDao(1L,
                    1L,
                    EmployeeType.CONTRACTOR.getValue(),
                    null,
                    new BigDecimal("50000")
            );
            case FULLTIMEEMPLOYEE -> new EmployeeDao(2L,
                    2L,
                    EmployeeType.FULLTIMEEMPLOYEE.getValue(),
                    null,
                    new BigDecimal("50000")
            );
            case PARTTIMEEMPLOYEE -> new EmployeeDao(3L,
                    3L,
                    EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                    9,
                    null
            );
        };
    }

    public static Employee getEmployeeWithType(EmployeeType employeeType) {
        return switch (employeeType) {
            case CONTRACTOR -> new ContractorEmployee(1L, new BigDecimal("50000"));
            case FULLTIMEEMPLOYEE -> new FullTimeEmployee(2L, new BigDecimal("50000"), 20);
            case PARTTIMEEMPLOYEE -> new PartTimeEmployee(3L, 9);
        };
    }

    public static EmployeeDTO getEmployeeDTOWithType(EmployeeType employeeType) {
        return switch (employeeType) {
            case CONTRACTOR -> new EmployeeDTO(1L,
                    EmployeeType.CONTRACTOR.getValue(),
                    null,
                    new BigDecimal("50000"),
                    new ContractorSalary(new BigDecimal("50000")).computeAnnualSalary(),
                    0
            );
            case FULLTIMEEMPLOYEE -> new EmployeeDTO(2L,
                    EmployeeType.FULLTIMEEMPLOYEE.getValue(),
                    null,
                    new BigDecimal("50000"),
                    new FullTimeSalary(new BigDecimal("50000")).computeAnnualSalary(),
                    20
            );
            case PARTTIMEEMPLOYEE -> new EmployeeDTO(3L,
                    EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                    9,
                    new PartTimeSalary(9).computeAnnualSalary(),
                    0
            );
        };
    }
}
