package com.example.demo.service;

import com.example.demo.domain.AddEmployeePayload;
import com.example.demo.domain.SamplePayload;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.models.EmployeeExport;
import com.example.demo.models.employee.ContractorEmployee;
import com.example.demo.models.employee.EmployeeType;
import com.example.demo.models.employee.FullTimeEmployee;
import com.example.demo.models.employee.PartTimeEmployee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    StockServiceClient stockServiceClient;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void shouldGetFullTimeEmployeeIfPresent() {
        long employeeId = 1L;

        EmployeeDao employeeDao = new EmployeeDao(employeeId, EmployeeType.FULLTIMEEMPLOYEE.getValue(), null,  new BigDecimal("10000"));
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(Optional.of(employeeDao));
        when(stockServiceClient.getStockCount()).thenReturn(ResponseEntity.ok(new SamplePayload(20)));

        assertThat(employeeService.getEmployee(employeeId))
                .isEqualTo(new FullTimeEmployee(1L, new BigDecimal("10000"), 20));
    }

    @Test
    void shouldHaveErrorIfNotPresent() {
        long employeeId = 1L;

        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> employeeService.getEmployee(employeeId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("employee with id 1 does not exists");
    }

    @Test
    void shouldGetPartTimeEmployeeIfPresent() {
        long employeeId = 1L;

        EmployeeDao employeeDao = new EmployeeDao(employeeId, EmployeeType.PARTTIMEEMPLOYEE.getValue(), 5, null);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(Optional.of(employeeDao));

        assertThat(employeeService.getEmployee(employeeId))
                .isEqualTo(new PartTimeEmployee(1L, 5));
    }

    @Test
    void shouldGetContractorEmployeeIfPresent() {
        long employeeId = 1L;

        EmployeeDao employeeDao = new EmployeeDao(employeeId, EmployeeType.CONTRACTOR.getValue(), null,  new BigDecimal("10000"));
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(Optional.of(employeeDao));

        assertThat(employeeService.getEmployee(employeeId))
                .isEqualTo(new ContractorEmployee(1L, new BigDecimal("10000")));
    }

    @Test
    void shouldGetAllEmployeesDataWithAnnualSalary() {
        List<EmployeeDao> employeeDaos = List.of(
                new EmployeeDao(1L, EmployeeType.CONTRACTOR.getValue(), null,  new BigDecimal("10000")),
                new EmployeeDao(2L, EmployeeType.PARTTIMEEMPLOYEE.getValue(), 5, null),
                new EmployeeDao(3L, EmployeeType.FULLTIMEEMPLOYEE.getValue(), null,  new BigDecimal("10000"))
        );
        when(employeeRepository.findAll()).thenReturn(employeeDaos);
        when(stockServiceClient.getStockCount()).thenReturn(ResponseEntity.ok(new SamplePayload(20)));

        List<EmployeeExport> expected = List.of(
                new EmployeeExport(1L, EmployeeType.CONTRACTOR.getValue(), null, new BigDecimal("10000"),  new BigDecimal("10000").setScale(2, RoundingMode.HALF_UP), 0),
                new EmployeeExport(2L, EmployeeType.PARTTIMEEMPLOYEE.getValue(), 5, null,  new BigDecimal("65000").setScale(2, RoundingMode.HALF_UP), 0),
                new EmployeeExport(3L, EmployeeType.FULLTIMEEMPLOYEE.getValue(), null, new BigDecimal("10000"),  new BigDecimal("12000").setScale(2, RoundingMode.HALF_UP), 20)
        );
        assertThat(employeeService.getAllEmployeesAsExport()).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("should add when is not existing")
    void shouldAddEmployee() {
        AddEmployeePayload addEmployeePayload = new AddEmployeePayload(1L, EmployeeType.CONTRACTOR.getValue(), null, new BigDecimal("10000"));

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(false);
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(new EmployeeDao(1L, EmployeeType.CONTRACTOR.getValue(), null, new BigDecimal("10000")));

        employeeService.addEmployee(addEmployeePayload);
        verify(employeeRepository).save(any(EmployeeDao.class));
    }

    @Test
    @DisplayName("should have error add when is existing")
    void shouldThrowExceptionWhenExisting() {
        AddEmployeePayload addEmployeePayload = new AddEmployeePayload(1L, EmployeeType.CONTRACTOR.getValue(), null, new BigDecimal("10000"));

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);

        assertThatThrownBy(() -> employeeService.addEmployee(addEmployeePayload))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("employee with id 1 already exists");
    }

    @Test
    @DisplayName("should update if existing")
    void shouldUpdate() {
        AddEmployeePayload addEmployeePayload = new AddEmployeePayload(1L, EmployeeType.CONTRACTOR.getValue(), null, new BigDecimal("10000"));

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(Optional.of(new EmployeeDao()));
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(new EmployeeDao());

        employeeService.updateEmployee(1L, addEmployeePayload);

        verify(employeeRepository).save(any(EmployeeDao.class));
    }

    @Test
    @DisplayName("should have error if not existing")
    void shouldNotUpdate() {
        AddEmployeePayload addEmployeePayload = new AddEmployeePayload(1L, EmployeeType.CONTRACTOR.getValue(), null, new BigDecimal("10000"));

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> employeeService.updateEmployee(1L, addEmployeePayload))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("employee 1 to modify does not exist");
    }

    @Test
    @DisplayName("should delete if existing")
    void shouldDelete() {
        long employeeId = 1L;

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(Optional.of(new EmployeeDao()));
        doNothing().when(employeeRepository).deleteById(anyLong());

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("should have error if not existing")
    void shouldNotDelete() {
        long employeeId = 1L;

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteEmployee(employeeId))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("employee 1 to delete does not exist");
    }
}