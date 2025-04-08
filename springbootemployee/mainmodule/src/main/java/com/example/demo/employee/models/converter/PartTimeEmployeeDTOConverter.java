package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.employee.models.employee.PartTimeEmployee;
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
    public Class<?> getSupportedEmployeeType() {
        return PartTimeEmployee.class;
    }
}
