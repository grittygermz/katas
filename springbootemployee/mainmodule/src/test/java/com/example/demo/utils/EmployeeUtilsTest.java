package com.example.demo.utils;

import com.example.demo.domain.EmployeeDTO;
import com.example.demo.domain.SamplePayload;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.models.employee.*;
import com.example.demo.models.salary.ContractorSalary;
import com.example.demo.models.salary.FullTimeSalary;
import com.example.demo.models.salary.PartTimeSalary;
import com.example.demo.service.StockServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeUtilsTest {

    @Mock
    StockServiceClient stockServiceClient;

    @InjectMocks
    EmployeeUtils employeeUtils;

    @Test
    void shouldConvertFullTimeEmployeeToEmployeeDTO() {
        EmployeeDTO employeeDTO = employeeUtils.convertEmployeeToEmployeeDTO(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE));
        assertThat(employeeDTO).isEqualTo(getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE));
    }

    @Test
    void shouldConvertContractorEmployeeToEmployeeDTO() {
        EmployeeDTO employeeDTO = employeeUtils.convertEmployeeToEmployeeDTO(getEmployeeWithType(EmployeeType.CONTRACTOR));
        assertThat(employeeDTO).isEqualTo(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));
    }

    @Test
    void shouldConvertPartTimeEmployeeToEmployeeDTO() {
        EmployeeDTO employeeDTO = employeeUtils.convertEmployeeToEmployeeDTO(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE));
        assertThat(employeeDTO).isEqualTo(getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE));
    }

    @Test
    void shouldConvertEmployeeDaoToFullTimeEmployee() {
        when(stockServiceClient.getStockCount()).thenReturn(ResponseEntity.ok(new SamplePayload(20)));

        Employee employee = employeeUtils.convertEmployeeDaoToEmployee(getEmployeeDaoWithType(EmployeeType.FULLTIMEEMPLOYEE));
        assertThat(employee).isEqualTo(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE));
    }

    @Test
    void shouldConvertEmployeeDaoToContractorEmployee() {
        Employee employee = employeeUtils.convertEmployeeDaoToEmployee(getEmployeeDaoWithType(EmployeeType.CONTRACTOR));
        assertThat(employee).isEqualTo(getEmployeeWithType(EmployeeType.CONTRACTOR));
    }

    @Test
    void shouldConvertEmployeeDaoToPartTimeEmployee() {
        Employee employee = employeeUtils.convertEmployeeDaoToEmployee(getEmployeeDaoWithType(EmployeeType.PARTTIMEEMPLOYEE));
        assertThat(employee).isEqualTo(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE));
    }

    @Test
    void shouldHaveRuntimeExceptionIfStockCountServiceIsUnavailable() {
        when(stockServiceClient.getStockCount()).thenReturn(ResponseEntity.internalServerError().build());

        assertThatThrownBy(() -> employeeUtils.convertEmployeeDaoToEmployee(getEmployeeDaoWithType(EmployeeType.FULLTIMEEMPLOYEE)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("failure in call to stockcount service");
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