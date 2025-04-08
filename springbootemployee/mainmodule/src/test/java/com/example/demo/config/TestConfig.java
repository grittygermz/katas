package com.example.demo.config;

import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.models.EmployeeDAOAdapter;
import com.example.demo.employee.models.EmployeeDTOAdapter;
import com.example.demo.employee.update.UpdateEmployeeService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public UpdateEmployeeService updateEmployeeService(EmployeeRepository employeeRepository, EmployeeDAOAdapter employeeDAOAdapter, EmployeeDTOAdapter employeeDTOAdapter) {
        return new UpdateEmployeeService(employeeRepository, employeeDAOAdapter, employeeDTOAdapter);
    }
}
