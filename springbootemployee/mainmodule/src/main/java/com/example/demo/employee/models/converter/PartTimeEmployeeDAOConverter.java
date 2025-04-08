package com.example.demo.employee.models.converter;

import com.example.demo.employee.EmployeeDao;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.employee.models.employee.PartTimeEmployee;
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
