package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.exchange.EmployeeDao;
import com.example.demo.employee.models.Employee;

public interface EmployeeDAOConverter {
    Employee createEmployee(EmployeeDao employeeDao);
    String getSupportedEmployeeType();
}