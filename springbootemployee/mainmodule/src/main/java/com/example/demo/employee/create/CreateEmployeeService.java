package com.example.demo.employee.create;

import com.example.demo.employee.models.EmployeeDao;
import com.example.demo.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreateEmployeeService {

    private final EmployeeRepository employeeRepository;

    public CreateEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public long addEmployee(CreateEmployeePayload createEmployeePayload) {

        if (!employeeRepository.existsByEmployeeId(createEmployeePayload.employeeId())) {
            EmployeeDao employeeDaoToSave = new EmployeeDao(createEmployeePayload.employeeId(),
                    createEmployeePayload.employeeType(),
                    createEmployeePayload.workingHoursPerDay(),
                    createEmployeePayload.baseSalary());
            return employeeRepository.save(employeeDaoToSave)
                    .getEmployeeId();
        } else {
            throw new EmployeeAlreadyExistsException("employee with id %s already exists".formatted(createEmployeePayload.employeeId()));
        }
    }
}
