package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.exchange.EmployeeDao;
import com.example.demo.employee.models.Employee;
import com.example.demo.employee.models.EmployeeType;
import com.example.demo.employee.models.PartTimeEmployee;
import org.springframework.stereotype.Component;

@Component
public class PartTimeEmployeeDAOConverter implements EmployeeDAOConverter {

    @Override
    public Employee createEmployee(EmployeeDao employeeDao) {
            return new PartTimeEmployee(
                    employeeDao.getEmployeeId(),
                    employeeDao.getWorkingHoursPerDay()
        );
    }

    @Override
    public String getSupportedEmployeeType() {
        return EmployeeType.PARTTIMEEMPLOYEE.getValue();
    }
}
