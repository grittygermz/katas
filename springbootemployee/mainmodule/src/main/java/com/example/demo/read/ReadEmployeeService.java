package com.example.demo.read;

import com.example.demo.domain.EmployeeDTO;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.models.employee.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.utils.EmployeeUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReadEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeUtils employeeUtils;

    public ReadEmployeeService(EmployeeRepository employeeRepository, EmployeeUtils employeeUtils) {
        this.employeeRepository = employeeRepository;
        this.employeeUtils = employeeUtils;
    }

    public EmployeeDTO getEmployee(long id) {
        Optional<EmployeeDao> employeeDaoOptional = employeeRepository.findByEmployeeId(id);
        if (employeeDaoOptional.isPresent()) {
            EmployeeDao employeeDao = employeeDaoOptional.get();

            Employee employee = employeeUtils.convertEmployeeDaoToEmployee(employeeDao);
            return employeeUtils.convertEmployeeToEmployeeDTO(employee);
        }
        throw new InvalidException("employee with id %s does not exists".formatted(id));
    }

    public List<EmployeeDTO> getAllEmployeesAsExport() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (EmployeeDao employeeDao : employeeRepository.findAll()) {
            Employee employee = employeeUtils.convertEmployeeDaoToEmployee(employeeDao);
            EmployeeDTO employeeDTO = employeeUtils.convertEmployeeToEmployeeDTO(employee);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }
}
