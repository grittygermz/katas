package com.example.demo.employee.models.exchange;

import com.example.demo.employee.models.Employee;
import com.example.demo.employee.models.EmployeeType;
import com.example.demo.employee.models.converter.EmployeeDTOConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeDTOAdapter {

    private final Map<EmployeeType, EmployeeDTOConverter> dtoConverterMap;

    public EmployeeDTOAdapter(List<EmployeeDTOConverter> employeeDTOConverters) {
        dtoConverterMap = new HashMap<>();
        for (EmployeeDTOConverter converter : employeeDTOConverters) {
            dtoConverterMap.put(converter.getSupportedEmployeeType(), converter);
        }
    }

    public EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTOConverter employeeDTOConverter = dtoConverterMap.get(employee.getEmployeeType());
        if (employeeDTOConverter == null) {
            throw new IllegalArgumentException("unsupported employee type");
        }
        return employeeDTOConverter.createEmployeeDTO(employee);

    }
}
