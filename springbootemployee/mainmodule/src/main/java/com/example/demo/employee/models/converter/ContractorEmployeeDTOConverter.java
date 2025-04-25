package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.exchange.EmployeeDTO;
import com.example.demo.employee.models.ContractorEmployee;
import com.example.demo.employee.models.Employee;
import com.example.demo.employee.models.EmployeeType;
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
                ((ContractorSalary) contractorEmployee.getSalary()).getBaseSalary(),
                contractorEmployee.getAnnualSalary(),
                0);
    }

    @Override
    public EmployeeType getSupportedEmployeeType() {
        return EmployeeType.CONTRACTOR;
    }
}
