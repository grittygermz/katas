package com.example.demo.employee.models.converter;

import com.example.demo.employee.models.exchange.EmployeeDao;
import com.example.demo.employee.models.Employee;
import com.example.demo.employee.models.EmployeeType;
import com.example.demo.employee.models.FullTimeEmployee;
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
