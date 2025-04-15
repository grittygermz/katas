package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.EmployeeDao;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.employee.models.employee.FullTimeEmployee;
import com.example.demo.stocks.StockServiceClient;
import org.springframework.stereotype.Component;

@Component
public class FullTimeEmployeeDAOConverter implements EmployeeDAOConverter {
    private final StockServiceClient stockServiceClient;

    public FullTimeEmployeeDAOConverter(StockServiceClient stockServiceClient) {
        this.stockServiceClient = stockServiceClient;
    }

    @Override
    public Employee createEmployee(EmployeeDao employeeDao) {
        return new FullTimeEmployee(
                employeeDao.getEmployeeId(),
                employeeDao.getBaseSalary(),
                stockServiceClient.retrieveStockCountFromService().stockCount()
        );
    }

    @Override
    public String getSupportedEmployeeType() {
        return EmployeeType.FULLTIMEEMPLOYEE.getValue();
    }
}
