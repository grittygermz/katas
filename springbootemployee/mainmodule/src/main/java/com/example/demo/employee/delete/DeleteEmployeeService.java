package com.example.demo.employee.delete;

import com.example.demo.employee.exceptions.EmployeeNotFoundException;
import com.example.demo.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteEmployeeService {
    private final EmployeeRepository employeeRepository;

    public DeleteEmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void deleteEmployee(long id) {
        if (employeeRepository.existsByEmployeeId(id)) {
            log.info("deleting employeeId {}", id);
            employeeRepository.deleteByEmployeeId(id);
        } else {
            throw new EmployeeNotFoundException("employee %s to delete does not exist".formatted(id));
        }
    }
}
