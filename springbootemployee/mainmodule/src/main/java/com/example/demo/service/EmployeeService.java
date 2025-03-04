package com.example.demo.service;

import com.example.demo.domain.AddEmployeePayload;
import com.example.demo.domain.SamplePayload;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.models.EmployeeExport;
import com.example.demo.models.employee.*;
import com.example.demo.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final StockServiceClient stockServiceClient;

    public EmployeeService(EmployeeRepository employeeRepository, StockServiceClient stockServiceClient) {
        this.employeeRepository = employeeRepository;
        this.stockServiceClient = stockServiceClient;
    }

    public Employee getEmployee(long id) {
        Optional<EmployeeDao> employeeDaoOptional = employeeRepository.findByEmployeeId(id);
        if (employeeDaoOptional.isPresent()) {
            EmployeeDao employeeDao = employeeDaoOptional.get();

            return convertEmployeeDaoToEmployee(employeeDao);
        }

        throw new InvalidException("employee with id %s does not exists".formatted(id));
    }

    public void addEmployee(AddEmployeePayload addEmployeePayload) {

        if (!employeeRepository.existsByEmployeeId(addEmployeePayload.employeeId())) {
            EmployeeDao employeeDaoToSave = new EmployeeDao(addEmployeePayload.employeeId(),
                    addEmployeePayload.employeeType(),
                    addEmployeePayload.workingHoursPerDay(),
                    addEmployeePayload.baseSalary());
            employeeRepository.save(employeeDaoToSave);
        } else {
            throw new InvalidException("employee with id %s already exists".formatted(addEmployeePayload.employeeId()));
        }
    }

    public void updateEmployee(long id, AddEmployeePayload addEmployeePayload) {
        if (employeeRepository.existsByEmployeeId(addEmployeePayload.employeeId())) {
            // does a dumb update and replaces all fields blindy
            EmployeeDao employeeDao = employeeRepository.findByEmployeeId(addEmployeePayload.employeeId()).get();
            long tableId = employeeDao.getId();

            EmployeeDao employeeDaoToSave = new EmployeeDao(tableId,
                    addEmployeePayload.employeeId(),
                    addEmployeePayload.employeeType(),
                    addEmployeePayload.workingHoursPerDay(),
                    addEmployeePayload.baseSalary());
            employeeRepository.save(employeeDaoToSave);
        } else {
            throw new InvalidException("employee %s to modify does not exist".formatted(id));
        }
    }

    @Transactional
    public void deleteEmployee(long id) {
        if (employeeRepository.existsByEmployeeId(id)) {
            EmployeeDao byEmployeeId = employeeRepository.findByEmployeeId(id).get();
            log.info("deleting employeeId {}", id);
            //employeeRepository.deleteByEmployeeId(id);
            employeeRepository.deleteById(byEmployeeId.getId());
        } else {
            throw new InvalidException("employee %s to delete does not exist".formatted(id));
        }
    }

    public List<EmployeeExport> getAllEmployeesAsExport() {
        List<EmployeeExport> employeeExportList = new ArrayList<>();

        for (EmployeeDao employeeDao : employeeRepository.findAll()) {
            Employee employee = convertEmployeeDaoToEmployee(employeeDao);
            EmployeeExport employeeExport = convertEmployeeToEmployeeExport(employeeDao, employee);
            employeeExportList.add(employeeExport);
        }
        return employeeExportList;
    }

    private EmployeeExport convertEmployeeToEmployeeExport(EmployeeDao employeeDao, Employee employee) {
        if (employee.getClass().equals(FullTimeEmployee.class)) {
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
            return new EmployeeExport(employeeDao.getEmployeeId(),
                    employeeDao.getEmployeeType(),
                    employeeDao.getWorkingHoursPerDay(),
                    employeeDao.getBaseSalary(),
                    fullTimeEmployee.getAnnualSalary(),
                    fullTimeEmployee.getStocks()
            );
        } else {
            return new EmployeeExport(employeeDao.getEmployeeId(),
                    employeeDao.getEmployeeType(),
                    employeeDao.getWorkingHoursPerDay(),
                    employeeDao.getBaseSalary(),
                    employee.getAnnualSalary(),
                    0
            );
        }
    }

    public SamplePayload retrieveStockCountFromService() {
        ResponseEntity<SamplePayload> stockCountResponse = stockServiceClient.getStockCount();
        if(stockCountResponse.getStatusCode().is2xxSuccessful()) {
            return stockCountResponse.getBody();
        }
        throw new RuntimeException("failure in call to stockcount service");
    }

    private Employee convertEmployeeDaoToEmployee(EmployeeDao employeeDao) {
        if (employeeDao.getEmployeeType().equals(EmployeeType.FULLTIMEEMPLOYEE.getValue())) {
            return new FullTimeEmployee(
                    employeeDao.getEmployeeId(),
                    employeeDao.getBaseSalary(),
                    retrieveStockCountFromService().stockCount()
            );
            //throw exception if stockcountservice is not available
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
}
