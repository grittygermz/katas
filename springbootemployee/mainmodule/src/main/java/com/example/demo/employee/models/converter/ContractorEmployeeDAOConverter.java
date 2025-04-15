package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.EmployeeDao;
import com.example.demo.employee.models.employee.ContractorEmployee;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
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
