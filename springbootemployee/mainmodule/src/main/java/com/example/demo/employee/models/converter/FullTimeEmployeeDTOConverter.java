package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.employee.models.employee.FullTimeEmployee;
import com.example.demo.employee.models.salary.FullTimeSalary;
import org.springframework.stereotype.Component;

@Component
public class FullTimeEmployeeDTOConverter implements EmployeeDTOConverter {

    @Override
    public EmployeeDTO createEmployeeDTO(Employee employee) {
        FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
        return new EmployeeDTO(fullTimeEmployee.getEmployeeId(),
                EmployeeType.FULLTIMEEMPLOYEE.getValue(),
                null,
                ((FullTimeSalary) fullTimeEmployee.getSalary()).baseSalary(),
                fullTimeEmployee.getAnnualSalary(),
                fullTimeEmployee.getStocks());
    }

    @Override
    public Class<?> getSupportedEmployeeType() {
        return FullTimeEmployee.class;
    }
}
