package com.example.demo.employee.update;

import com.example.demo.config.TestConfig;
import com.example.demo.employee.EmployeeDao;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.exceptions.InvalidException;
import com.example.demo.employee.models.EmployeeDAOAdapter;
import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.EmployeeDTOAdapter;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.example.demo.employee.helpers.DummyObjectFactory.getEmployeeDTOWithType;
import static com.example.demo.employee.helpers.DummyObjectFactory.getEmployeeWithType;
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
    EmployeeDAOAdapter employeeDAOAdapter;

    @MockitoBean
    EmployeeDTOAdapter employeeDTOAdapter;

    @Test
    @DisplayName("put request with contractor -> contractor")
    void shouldUpdateEmployeeWithPutRequest() {
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.of(new EmployeeDao(1L,
                        EmployeeType.CONTRACTOR.getValue(),
                        null,
                        new BigDecimal("49999")))
        );
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(new EmployeeDao(1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50000"))
        );
        when(employeeDAOAdapter.getEmployee(any(EmployeeDao.class))).thenReturn(getEmployeeWithType(EmployeeType.CONTRACTOR));
        when(employeeDTOAdapter.getEmployeeDTO(any(Employee.class))).thenReturn(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));

        EmployeeDTO employeeDTO = updateEmployeeService.updateEmployee(1L,
                new PutEmployeePayload(EmployeeType.CONTRACTOR.getValue(),
                        null,
                        new BigDecimal("50000"))
        );
        assertThat(employeeDTO).isEqualTo(new EmployeeDTO(
                1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50000"),
                new BigDecimal("50000"),
                0)
        );
    }

    @Test
    @DisplayName("put request with contractor -> parttimeemployee")
    void shouldUpdateEmployeeWithPutRequest1() {
        when(employeeRepository.existsByEmployeeId(anyLong())).thenReturn(true);
        when(employeeRepository.findByEmployeeId(anyLong())).thenReturn(
                Optional.of(new EmployeeDao(3, 1L,
                        EmployeeType.CONTRACTOR.getValue(),
                        null,
                        new BigDecimal("49999")))
        );
        when(employeeRepository.save(any(EmployeeDao.class))).thenReturn(new EmployeeDao(3,3L,
                EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                9,
                null)
        );
        when(employeeDAOAdapter.getEmployee(any(EmployeeDao.class))).thenReturn(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE));
        when(employeeDTOAdapter.getEmployeeDTO(any(Employee.class))).thenReturn(getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE));

        EmployeeDTO employeeDTO = updateEmployeeService.updateEmployee(3L,
                new PutEmployeePayload(EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                        9,
                        null)
        );
        assertThat(employeeDTO).isEqualTo(new EmployeeDTO(
                3L,
                EmployeeType.PARTTIMEEMPLOYEE.getValue(),
                9,
                new BigDecimal("117000"),
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

        when(employeeDAOAdapter.getEmployee(any(EmployeeDao.class))).thenReturn(getEmployeeWithType(EmployeeType.CONTRACTOR));
        when(employeeDTOAdapter.getEmployeeDTO(any(Employee.class))).thenReturn(getEmployeeDTOWithType(EmployeeType.CONTRACTOR));

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