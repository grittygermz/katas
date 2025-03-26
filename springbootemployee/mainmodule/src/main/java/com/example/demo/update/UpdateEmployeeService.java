package com.example.demo.update;

import com.example.demo.domain.EmployeeDTO;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.models.employee.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.utils.EmployeeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Slf4j
public class UpdateEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeUtils employeeUtils;

    public UpdateEmployeeService(EmployeeRepository employeeRepository, EmployeeUtils employeeUtils) {
        this.employeeRepository = employeeRepository;
        this.employeeUtils = employeeUtils;
    }

    public EmployeeDTO updateEmployee(long employeeId, PutEmployeePayload putEmployeePayload) {
        if (employeeRepository.existsByEmployeeId(employeeId)) {

            EmployeeDao existingEmployeeDao = employeeRepository.findByEmployeeId(employeeId).get();
            long tableId = existingEmployeeDao.getId();

            EmployeeDao employeeDaoToSave = createEmployeeDaoWithAllFieldsOverriden(employeeId, putEmployeePayload, tableId);

            EmployeeDao savedEmployeeDao = employeeRepository.save(employeeDaoToSave);
            Employee employee = employeeUtils.convertEmployeeDaoToEmployee(savedEmployeeDao);
            return employeeUtils.convertEmployeeToEmployeeDTO(employee);
        } else {
            throw new InvalidException("employee with id %s to modify does not exist".formatted(employeeId));
        }
    }

    public EmployeeDTO updateEmployee(long employeeId, PatchEmployeePayload patchEmployeePayload) {
        if (employeeRepository.existsByEmployeeId(employeeId)) {

            EmployeeDao existingEmployeeDao = employeeRepository.findByEmployeeId(employeeId).get();

            EmployeeDao employeeDaoToSave = null;

            try {
                employeeDaoToSave = createEmployeeDaoWithOverridenFieldsIfProvided(existingEmployeeDao, patchEmployeePayload);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            EmployeeDao savedEmployeeDao = employeeRepository.save(employeeDaoToSave);
            Employee employee = employeeUtils.convertEmployeeDaoToEmployee(savedEmployeeDao);
            return employeeUtils.convertEmployeeToEmployeeDTO(employee);
        } else {
            throw new InvalidException("employee with id %s to modify does not exist".formatted(employeeId));
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
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return newEmployeeDao;
    }
}
