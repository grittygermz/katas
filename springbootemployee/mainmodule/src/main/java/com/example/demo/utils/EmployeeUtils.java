package com.example.demo.utils;

import com.example.demo.domain.SamplePayload;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.domain.EmployeeDTO;
import com.example.demo.models.employee.*;
import com.example.demo.models.salary.ContractorSalary;
import com.example.demo.models.salary.FullTimeSalary;
import com.example.demo.service.StockServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUtils {

    private final StockServiceClient stockServiceClient;

    public EmployeeUtils(StockServiceClient stockServiceClient) {
        this.stockServiceClient = stockServiceClient;
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        if (employee.getClass().equals(FullTimeEmployee.class)) {
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
            return new EmployeeDTO(fullTimeEmployee.getEmployeeId(),
                    EmployeeType.FULLTIMEEMPLOYEE.getValue(),
                    null,
                    ((FullTimeSalary)fullTimeEmployee.getSalary()).baseSalary(),
                    fullTimeEmployee.getAnnualSalary(),
                    fullTimeEmployee.getStocks());

        } else if(employee.getClass().equals(ContractorEmployee.class)) {
            ContractorEmployee contractorEmployee = (ContractorEmployee) employee;
            return new EmployeeDTO(contractorEmployee.getEmployeeId(),
                    EmployeeType.CONTRACTOR.getValue(),
                    null,
                    ((ContractorSalary)contractorEmployee.getSalary()).baseSalary(),
                    contractorEmployee.getAnnualSalary(),
                    0);

        } else {
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
            return new EmployeeDTO(partTimeEmployee.getEmployeeId(),
                    EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                    partTimeEmployee.getWorkingHoursPerDay(),
                    partTimeEmployee.getAnnualSalary(),
                    0);
        }
    }

    public Employee convertEmployeeDaoToEmployee(EmployeeDao employeeDao) {
        if (employeeDao.getEmployeeType().equals(EmployeeType.FULLTIMEEMPLOYEE.getValue())) {
            return new FullTimeEmployee(
                    employeeDao.getEmployeeId(),
                    employeeDao.getBaseSalary(),
                    retrieveStockCountFromService().stockCount()
            );
        } else if (employeeDao.getEmployeeType().equals(EmployeeType.PARTTIMEEMPLOYEE.getValue())) {
            return new PartTimeEmployee(
                    employeeDao.getEmployeeId(),
                    employeeDao.getWorkingHoursPerDay()
            );
        } else {
            return new ContractorEmployee(
                    employeeDao.getEmployeeId(),
                    employeeDao.getBaseSalary()
            );
        }
    }

    private SamplePayload retrieveStockCountFromService() {
        ResponseEntity<SamplePayload> stockCountResponse = stockServiceClient.getStockCount();
        if(stockCountResponse.getStatusCode().is2xxSuccessful()) {
            return stockCountResponse.getBody();
        }
        throw new RuntimeException("failure in call to stockcount service");
    }


}
