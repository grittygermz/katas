package com.example.demo.employee.models;

import com.example.demo.employee.models.converter.EmployeeDTOConverter;
import com.example.demo.employee.models.employee.Employee;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeDTOAdapter {

    private Map<Class<?>, EmployeeDTOConverter> dtoConverterMap;

    public EmployeeDTOAdapter(List<EmployeeDTOConverter> employeeDTOConverters) {
        dtoConverterMap = new HashMap<>();
        for (EmployeeDTOConverter converter : employeeDTOConverters) {
            dtoConverterMap.put(converter.getSupportedEmployeeType(), converter);
        }
    }

    public EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTOConverter employeeDTOConverter = dtoConverterMap.get(employee.getClass());
        if (employeeDTOConverter == null) {
            throw new IllegalArgumentException("unsupported employee type");
        }
        return employeeDTOConverter.createEmployeeDTO(employee);

    }
}
