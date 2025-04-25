package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.exchange.EmployeeDao;
import com.example.demo.employee.models.ContractorEmployee;
import com.example.demo.employee.models.Employee;
import com.example.demo.employee.models.EmployeeType;
import org.springframework.stereotype.Component;

@Component
public class ContractorEmployeeDAOConverter implements EmployeeDAOConverter {

    @Override
    public String getSupportedEmployeeType() {
        return EmployeeType.CONTRACTOR.getValue();
    }

    @Override
    public Employee createEmployee(EmployeeDao employeeDao) {
        return new ContractorEmployee(
                employeeDao.getEmployeeId(),
                employeeDao.getBaseSalary()
        );


    }
}
