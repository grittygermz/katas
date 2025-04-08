package com.example.demo.employee.models.converter;

import com.example.demo.employee.EmployeeDao;
import com.example.demo.employee.models.employee.Employee;

public interface EmployeeDAOConverter {
    Employee createEmployee(EmployeeDao employeeDao);
    String getSupportedEmployeeType();
}