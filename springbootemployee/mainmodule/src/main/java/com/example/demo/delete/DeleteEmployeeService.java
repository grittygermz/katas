package com.example.demo.delete;

import com.example.demo.exceptions.InvalidException;
import com.example.demo.repository.EmployeeRepository;
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
            throw new InvalidException("employee %s to delete does not exist".formatted(id));
        }
    }
}
