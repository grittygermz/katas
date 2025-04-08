package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.employee.ContractorEmployee;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.employee.models.salary.ContractorSalary;
import org.springframework.stereotype.Component;

@Component
public class ContractorEmployeeDTOConverter implements EmployeeDTOConverter {

    @Override
    public EmployeeDTO createEmployeeDTO(Employee employee) {
        ContractorEmployee contractorEmployee = (ContractorEmployee) employee;
        return new EmployeeDTO(contractorEmployee.getEmployeeId(),
                EmployeeType.CONTRACTOR.getValue(),
                null,
                ((ContractorSalary) contractorEmployee.getSalary()).baseSalary(),
                contractorEmployee.getAnnualSalary(),
                0);
    }

    @Override
    public Class<?> getSupportedEmployeeType() {
        return ContractorEmployee.class;
    }
}
