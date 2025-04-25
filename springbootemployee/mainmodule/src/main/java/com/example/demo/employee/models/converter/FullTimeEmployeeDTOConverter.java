package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.exchange.EmployeeDTO;
import com.example.demo.employee.models.Employee;
import com.example.demo.employee.models.EmployeeType;
import com.example.demo.employee.models.FullTimeEmployee;
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
                ((FullTimeSalary) fullTimeEmployee.getSalary()).getBaseSalary(),
                fullTimeEmployee.getAnnualSalary(),
                fullTimeEmployee.getStocks());
    }

    @Override
    public EmployeeType getSupportedEmployeeType() {
        return EmployeeType.FULLTIMEEMPLOYEE;
    }
}
