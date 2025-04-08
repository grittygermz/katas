package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.employee.Employee;

public interface EmployeeDTOConverter {
    EmployeeDTO createEmployeeDTO(Employee employee);
    Class<?> getSupportedEmployeeType();
}
