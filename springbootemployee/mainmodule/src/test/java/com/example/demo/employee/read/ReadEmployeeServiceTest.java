package com.example.demo.employee.read;

import com.example.demo.employee.models.EmployeeDao;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.exceptions.EmployeeNotFoundException;
import com.example.demo.employee.models.EmployeeDAOAdapter;
import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.EmployeeDTOAdapter;
import com.example.demo.employee.models.employee.*;
import com.example.demo.employee.models.salary.ContractorSalary;
import com.example.demo.employee.models.salary.FullTimeSalary;
import com.example.demo.employee.models.salary.PartTimeSalary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * mocks employeeUtil as well.. but feels like it's just testing a bunch of if statements
 */
@ExtendWith(MockitoExtension.class)
class ReadEmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmployeeDTOAdapter employeeDTOAdapter;
    @Mock
    EmployeeDAOAdapter employeeDAOAdapter;

    @InjectMocks
    ReadEmployeeService readEmployeeService;

    @Test
    void shouldGetEmployeeWhenIdIsPresent() {
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.of(getEmployeeDaoWithType(EmployeeType.CONTRACTOR)));
        when(employeeDAOAdapter.getEmployee(any())).thenReturn(getEmployeeWithType(EmployeeType.CONTRACTOR));
        when(employeeDTOAdapter.getEmployeeDTO(any())).thenReturn(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));

        EmployeeDTO employee = readEmployeeService.getEmployee(1L);
        assertThat(employee).isEqualTo(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));
    }

    @Test
    void shouldThrowErrorIfEmployeeIsNotPresent() {
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.empty());
        assertThatThrownBy(() -> readEmployeeService.getEmployee(1L)).isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("employee with id 1 does not exists");
    }

    @Test
    void shouldGetListOfAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(
                List.of(
                        getEmployeeDaoWithType(EmployeeType.CONTRACTOR),
                        getEmployeeDaoWithType(EmployeeType.FULLTIMEEMPLOYEE),
                        getEmployeeDaoWithType(EmployeeType.PARTTIMEEMPLOYEE)
                ));
        when(employeeDAOAdapter.getEmployee(getEmployeeDaoWithType(EmployeeType.CONTRACTOR)))
                .thenReturn(getEmployeeWithType(EmployeeType.CONTRACTOR));
        when(employeeDAOAdapter.getEmployee(getEmployeeDaoWithType(EmployeeType.FULLTIMEEMPLOYEE)))
                .thenReturn(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE));
        when(employeeDAOAdapter.getEmployee(getEmployeeDaoWithType(EmployeeType.PARTTIMEEMPLOYEE)))
                .thenReturn(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE));
        when((employeeDTOAdapter.getEmployeeDTO(getEmployeeWithType(EmployeeType.CONTRACTOR))))
                .thenReturn(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));
        when((employeeDTOAdapter.getEmployeeDTO(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE))))
                .thenReturn(getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE));
        when((employeeDTOAdapter.getEmployeeDTO(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE))))
                .thenReturn(getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE));

        System.out.println(readEmployeeService.getAllEmployeesAsExport());

        assertThat(readEmployeeService.getAllEmployeesAsExport()).containsExactlyElementsOf(
                List.of(
                        getEmployeeDTOWithType(EmployeeType.CONTRACTOR),
                        getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE),
                        getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE)
                )
        );
    }

    private EmployeeDao getEmployeeDaoWithType(EmployeeType employeeType) {
        return switch (employeeType) {
            case CONTRACTOR -> new EmployeeDao(1L,
                    1L,
                    EmployeeType.CONTRACTOR.getValue(),
                    null,
                    new BigDecimal("50000")
            );
            case FULLTIMEEMPLOYEE -> new EmployeeDao(2L,
                    2L,
                    EmployeeType.FULLTIMEEMPLOYEE.getValue(),
                    null,
                    new BigDecimal("50000")
            );
            case PARTTIMEEMPLOYEE -> new EmployeeDao(3L,
                    3L,
                    EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                    9,
                    null
            );
        };
    }

    private Employee getEmployeeWithType(EmployeeType employeeType) {
        return switch (employeeType) {
            case CONTRACTOR -> new ContractorEmployee(1L, new BigDecimal("50000"));
            case FULLTIMEEMPLOYEE -> new FullTimeEmployee(2L, new BigDecimal("50000"), 20);
            case PARTTIMEEMPLOYEE -> new PartTimeEmployee(3L, 9);
        };
    }

    private EmployeeDTO getEmployeeDTOWithType(EmployeeType employeeType) {
        return switch (employeeType) {
            case CONTRACTOR -> new EmployeeDTO(1L,
                    EmployeeType.CONTRACTOR.getValue(),
                    null,
                    new BigDecimal("50000"),
                    new ContractorSalary(new BigDecimal("50000")).computeAnnualSalary(),
                    0
            );
            case FULLTIMEEMPLOYEE -> new EmployeeDTO(2L,
                    EmployeeType.FULLTIMEEMPLOYEE.getValue(),
                    null,
                    new BigDecimal("50000"),
                    new FullTimeSalary(new BigDecimal("50000")).computeAnnualSalary(),
                    20
            );
            case PARTTIMEEMPLOYEE -> new EmployeeDTO(3L,
                    EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                    9,
                    new PartTimeSalary(9).computeAnnualSalary(),
                    0
            );
        };
    }


}