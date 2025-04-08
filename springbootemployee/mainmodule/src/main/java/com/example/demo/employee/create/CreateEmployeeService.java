package com.example.demo.employee.create;

import com.example.demo.employee.EmployeeDao;
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

    public long addEmployee(AddEmployeePayload addEmployeePayload) {

        if (!employeeRepository.existsByEmployeeId(addEmployeePayload.employeeId())) {
            EmployeeDao employeeDaoToSave = new EmployeeDao(addEmployeePayload.employeeId(),
                    addEmployeePayload.employeeType(),
                    addEmployeePayload.workingHoursPerDay(),
                    addEmployeePayload.baseSalary());
            return employeeRepository.save(employeeDaoToSave)
                    .getEmployeeId();
        } else {
            throw new ConflictingException("employee with id %s already exists".formatted(addEmployeePayload.employeeId()));
        }
    }
}
