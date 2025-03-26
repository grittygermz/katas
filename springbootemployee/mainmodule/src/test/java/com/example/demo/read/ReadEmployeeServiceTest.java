package com.example.demo.read;

import com.example.demo.domain.EmployeeDTO;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.models.employee.*;
import com.example.demo.models.salary.ContractorSalary;
import com.example.demo.models.salary.FullTimeSalary;
import com.example.demo.models.salary.PartTimeSalary;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.utils.EmployeeUtils;
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
    EmployeeUtils employeeUtils;

    @InjectMocks
    ReadEmployeeService readEmployeeService;

    @Test
    void shouldGetEmployeeWhenIdIsPresent() {
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.of(getEmployeeDaoWithType(EmployeeType.CONTRACTOR)));
        when(employeeUtils.convertEmployeeDaoToEmployee(any())).thenReturn(getEmployeeWithType(EmployeeType.CONTRACTOR));
        when(employeeUtils.convertEmployeeToEmployeeDTO(any())).thenReturn(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));

        EmployeeDTO employee = readEmployeeService.getEmployee(1L);
        assertThat(employee).isEqualTo(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));
    }

    @Test
    void shouldThrowErrorIfEmployeeIsNotPresent() {
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.empty());
        assertThatThrownBy(() -> readEmployeeService.getEmployee(1L)).isInstanceOf(InvalidException.class)
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
        when(employeeUtils.convertEmployeeDaoToEmployee(getEmployeeDaoWithType(EmployeeType.CONTRACTOR)))
                .thenReturn(getEmployeeWithType(EmployeeType.CONTRACTOR));
        when(employeeUtils.convertEmployeeDaoToEmployee(getEmployeeDaoWithType(EmployeeType.FULLTIMEEMPLOYEE)))
                .thenReturn(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE));
        when(employeeUtils.convertEmployeeDaoToEmployee(getEmployeeDaoWithType(EmployeeType.PARTTIMEEMPLOYEE)))
                .thenReturn(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE));
        when(employeeUtils.convertEmployeeToEmployeeDTO(getEmployeeWithType(EmployeeType.CONTRACTOR)))
                .thenReturn(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));
        when(employeeUtils.convertEmployeeToEmployeeDTO(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE)))
                .thenReturn(getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE));
        when(employeeUtils.convertEmployeeToEmployeeDTO(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE)))
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