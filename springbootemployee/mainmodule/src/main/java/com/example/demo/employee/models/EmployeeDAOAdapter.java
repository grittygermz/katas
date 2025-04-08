package com.example.demo.employee.models;

import com.example.demo.employee.EmployeeDao;
import com.example.demo.employee.models.converter.*;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.stocks.StockServiceClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeDAOAdapter {

    private final Map<String, EmployeeDAOConverter> daoConverterMap;

    public EmployeeDAOAdapter(List<EmployeeDAOConverter> converters, StockServiceClient stockServiceClient) {
        daoConverterMap = new HashMap<>();
        for (EmployeeDAOConverter converter: converters) {
            daoConverterMap.put(converter.getSupportedEmployeeType(), converter);
        }
    }


    public Employee getEmployee(EmployeeDao employeeDao) {
        EmployeeDAOConverter employeeDAOConverter = daoConverterMap.get(employeeDao.getEmployeeType());
        if(employeeDAOConverter == null) {
            throw new IllegalArgumentException("unsupported employee type");
        }
        return employeeDAOConverter.createEmployee(employeeDao);
    }

}
