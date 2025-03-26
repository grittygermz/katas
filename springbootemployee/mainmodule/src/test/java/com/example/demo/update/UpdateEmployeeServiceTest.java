package com.example.demo.update;

import com.example.demo.config.TestConfig;
import com.example.demo.domain.EmployeeDTO;
import com.example.demo.domain.SamplePayload;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.exceptions.InvalidException;
import com.example.demo.models.employee.EmployeeType;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.StockServiceClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * kind of like an integration test? tests updateemployeeservice + employeeUtils?
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class UpdateEmployeeServiceTest {

    @MockitoBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private UpdateEmployeeService updateEmployeeService;

    @MockitoBean
    private StockServiceClient stockServiceClient;

    @Test
    @DisplayName("put request with contractor -> contractor")
    void shouldUpdateEmployeeWithPutRequest() {
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.of(new EmployeeDao(1L,
                        EmployeeType.CONTRACTOR.getValue(),
                        null,
                        new BigDecimal("50000")))
        );
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(new EmployeeDao(1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50001"))
        );

        EmployeeDTO employeeDTO = updateEmployeeService.updateEmployee(1L,
                new PutEmployeePayload(EmployeeType.CONTRACTOR.getValue(),
                        null,
                        new BigDecimal("50001"))
        );
        assertThat(employeeDTO).isEqualTo(new EmployeeDTO(
                1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50001"),
                new BigDecimal("50001"),
                0)
        );
    }

    @Test
    @DisplayName("put request with contractor -> parttimeemployee")
    void shouldUpdateEmployeeWithPutRequest1() {
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.of(new EmployeeDao(1, 1L,
                        EmployeeType.CONTRACTOR.getValue(),
                        null,
                        new BigDecimal("50000")))
        );
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(new EmployeeDao(1,1L,
                EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                5,
                null)
        );

        EmployeeDTO employeeDTO = updateEmployeeService.updateEmployee(1L,
                new PutEmployeePayload(EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                        5,
                        null)
        );
        assertThat(employeeDTO).isEqualTo(new EmployeeDTO(
                1L,
                EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                5,
                new BigDecimal("65000"),
                0)
        );
    }

    @Test
    @DisplayName("can save even if baseSalary is not provided and employee type is changed from full time to contractor")
    void shouldUpdateEmployeeWithPatchRequest() {
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.of(new EmployeeDao(1L,
                        EmployeeType.FULLTIMEEMPLOYEE.getValue(),
                        null,
                        new BigDecimal("50000")))
        );
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(new EmployeeDao(1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50000"))
        );
        when(stockServiceClient.getStockCount()).thenReturn(ResponseEntity.ok(new SamplePayload(20)));

        EmployeeDTO employeeDTO = updateEmployeeService.updateEmployee(1L,
                new PatchEmployeePayload(EmployeeType.CONTRACTOR.getValue(),
                        null,
                        null)
        );
        assertThat(employeeDTO).isEqualTo(new EmployeeDTO(
                1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50000.00"),
                new BigDecimal("50000.00"),
                0)
        );
    }

    @Test
    @DisplayName("should have error add when trying to patch non-existing")
    void shouldThrowExceptionWhenExistingForPatch() {

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> updateEmployeeService.updateEmployee(1L,
                new PatchEmployeePayload(EmployeeType.CONTRACTOR.getValue(),
                        null,
                        null)
        ))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("employee with id 1 to modify does not exist");
    }

    @Test
    @DisplayName("should have error add when trying to put non-existing")
    void shouldThrowExceptionWhenExistingForPut() {

        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> updateEmployeeService.updateEmployee(1L,
                new PutEmployeePayload(EmployeeType.CONTRACTOR.getValue(),
                        null,
                        null)
        ))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("employee with id 1 to modify does not exist");
    }

}