package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.exchange.EmployeeDTO;
import com.example.demo.employee.models.Employee;
import com.example.demo.employee.models.EmployeeType;
import com.example.demo.employee.models.PartTimeEmployee;
import org.springframework.stereotype.Component;

@Component
public class PartTimeEmployeeDTOConverter implements EmployeeDTOConverter {

    @Override
    public EmployeeDTO createEmployeeDTO(Employee employee) {
        PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
        return new EmployeeDTO(partTimeEmployee.getEmployeeId(),
                EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                partTimeEmployee.getWorkingHoursPerDay(),
                partTimeEmployee.getAnnualSalary(),
                0);
    }

    @Override
    public EmployeeType getSupportedEmployeeType() {
        return EmployeeType.PARTTIMEEMPLOYEE;
    }
}
