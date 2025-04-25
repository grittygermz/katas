package com.example.demo.employee.update;

import com.example.demo.employee.models.exchange.EmployeeDao;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.exceptions.EmployeeNotFoundException;
import com.example.demo.employee.models.exchange.EmployeeDAOAdapter;
import com.example.demo.employee.models.exchange.EmployeeDTO;
import com.example.demo.employee.models.exchange.EmployeeDTOAdapter;
import com.example.demo.employee.models.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Slf4j
public class UpdateEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDAOAdapter employeeDAOAdapter;
    private final EmployeeDTOAdapter employeeDTOAdapter;

    public UpdateEmployeeService(EmployeeRepository employeeRepository, EmployeeDAOAdapter employeeDAOAdapter, EmployeeDTOAdapter employeeDTOAdapter) {
        this.employeeRepository = employeeRepository;
        this.employeeDAOAdapter = employeeDAOAdapter;
        this.employeeDTOAdapter = employeeDTOAdapter;
    }

    public EmployeeDTO updateEmployee(long employeeId, PutEmployeePayload putEmployeePayload) {
        if (employeeRepository.existsByEmployeeId(employeeId)) {

            EmployeeDao existingEmployeeDao = employeeRepository.findByEmployeeId(employeeId).get();
            long tableId = existingEmployeeDao.getId();

            EmployeeDao employeeDaoToSave = createEmployeeDaoWithAllFieldsOverriden(employeeId, putEmployeePayload, tableId);

            EmployeeDao savedEmployeeDao = employeeRepository.save(employeeDaoToSave);
            Employee employee = employeeDAOAdapter.getEmployee(savedEmployeeDao);
            return employeeDTOAdapter.getEmployeeDTO(employee);
        } else {
            throw new EmployeeNotFoundException("employee with id %s to modify does not exist".formatted(employeeId));
        }
    }

    public EmployeeDTO updateEmployee(long employeeId, PatchEmployeePayload patchEmployeePayload) {
        if (employeeRepository.existsByEmployeeId(employeeId)) {

            EmployeeDao existingEmployeeDao = employeeRepository.findByEmployeeId(employeeId).get();

            EmployeeDao employeeDaoToSave = null;

            try {
                employeeDaoToSave = createEmployeeDaoWithOverridenFieldsIfProvided(existingEmployeeDao, patchEmployeePayload);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }

            EmployeeDao savedEmployeeDao = employeeRepository.save(employeeDaoToSave);
            Employee employee = employeeDAOAdapter.getEmployee(savedEmployeeDao);
            return employeeDTOAdapter.getEmployeeDTO(employee);
        } else {
            throw new EmployeeNotFoundException("employee with id %s to modify does not exist".formatted(employeeId));
        }
    }

    private EmployeeDao createEmployeeDaoWithAllFieldsOverriden(long employeeId, PutEmployeePayload putEmployeePayload, long tableId) {
        EmployeeDao employeeDaoToSave;
        employeeDaoToSave = new EmployeeDao(tableId,
                employeeId,
                putEmployeePayload.employeeType(),
                putEmployeePayload.workingHoursPerDay(),
                putEmployeePayload.baseSalary());
        return employeeDaoToSave;
    }

    private EmployeeDao createEmployeeDaoWithOverridenFieldsIfProvided(EmployeeDao existingEmployeeDao, PatchEmployeePayload patchEmployeePayload) throws IllegalAccessException {
        EmployeeDao newEmployeeDao = new EmployeeDao(existingEmployeeDao);

        Field[] fields = patchEmployeePayload.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(patchEmployeePayload);
            if (value != null) {
                try {
                    String fieldName = field.getName();
                    Field fieldToOverride = newEmployeeDao.getClass().getDeclaredField(fieldName);
                    fieldToOverride.setAccessible(true);
                    fieldToOverride.set(newEmployeeDao, value);
                } catch (NoSuchFieldException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return newEmployeeDao;
    }
}
