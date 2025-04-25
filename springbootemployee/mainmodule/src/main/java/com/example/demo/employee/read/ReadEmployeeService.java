package com.example.demo.employee.read;

import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.exceptions.EmployeeNotFoundException;
import com.example.demo.employee.models.exchange.EmployeeDAOAdapter;
import com.example.demo.employee.models.exchange.EmployeeDTO;
import com.example.demo.employee.models.exchange.EmployeeDTOAdapter;
import com.example.demo.employee.models.exchange.EmployeeDao;
import com.example.demo.employee.models.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReadEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDAOAdapter employeeDAOAdapter;
    private final EmployeeDTOAdapter employeeDTOAdapter;

    public ReadEmployeeService(EmployeeRepository employeeRepository, EmployeeDAOAdapter employeeDAOAdapter, EmployeeDTOAdapter employeeDTOAdapter) {
        this.employeeRepository = employeeRepository;
        this.employeeDAOAdapter = employeeDAOAdapter;
        this.employeeDTOAdapter = employeeDTOAdapter;
    }

    public EmployeeDTO getEmployee(long id) {
        Optional<EmployeeDao> employeeDaoOptional = employeeRepository.findByEmployeeId(id);
        if (employeeDaoOptional.isPresent()) {
            EmployeeDao employeeDao = employeeDaoOptional.get();

            Employee employee = employeeDAOAdapter.getEmployee(employeeDao);
            return employeeDTOAdapter.getEmployeeDTO(employee);
        }
        throw new EmployeeNotFoundException("employee with id %s does not exists".formatted(id));
    }

    public List<EmployeeDTO> getAllEmployeesAsExport() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (EmployeeDao employeeDao : employeeRepository.findAll()) {
            Employee employee = employeeDAOAdapter.getEmployee(employeeDao);
            EmployeeDTO employeeDTO = employeeDTOAdapter.getEmployeeDTO(employee);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }
}
