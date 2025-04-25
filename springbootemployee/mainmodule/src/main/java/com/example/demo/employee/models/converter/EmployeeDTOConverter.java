package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.EmployeeType;
import com.example.demo.employee.models.exchange.EmployeeDTO;
import com.example.demo.employee.models.Employee;

public interface EmployeeDTOConverter {
    EmployeeDTO createEmployeeDTO(Employee employee);
    EmployeeType getSupportedEmployeeType();
}
