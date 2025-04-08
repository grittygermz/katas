package com.example.demo.employee.models;

import com.example.demo.employee.EmployeeDao;
import com.example.demo.employee.models.employee.Employee;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.stocks.SamplePayload;
import com.example.demo.stocks.StockServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;

import static com.example.demo.employee.helpers.DummyObjectFactory.getEmployeeDaoWithType;
import static com.example.demo.employee.helpers.DummyObjectFactory.getEmployeeWithType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
class EmployeeDaoAdapterTest {

    @Autowired
    EmployeeDAOAdapter employeeDAOAdapter;

    @MockitoBean
    private StockServiceClient stockServiceClient;

    @Test
    void shouldConvertEmployeeDaoToFullTimeEmployee() {
        when(stockServiceClient.retrieveStockCountFromService()).thenReturn(new SamplePayload(20));

        Employee employee = employeeDAOAdapter.getEmployee(getEmployeeDaoWithType(EmployeeType.FULLTIMEEMPLOYEE));
        assertThat(employee).isEqualTo(getEmployeeWithType(EmployeeType.FULLTIMEEMPLOYEE));
    }

    @Test
    void shouldConvertEmployeeDaoToContractorEmployee() {
        Employee employee = employeeDAOAdapter.getEmployee(getEmployeeDaoWithType(EmployeeType.CONTRACTOR));
        assertThat(employee).isEqualTo(getEmployeeWithType(EmployeeType.CONTRACTOR));
    }

    @Test
    void shouldConvertEmployeeDaoToPartTimeEmployee() {
        Employee employee = employeeDAOAdapter.getEmployee(getEmployeeDaoWithType(EmployeeType.PARTTIMEEMPLOYEE));
        assertThat(employee).isEqualTo(getEmployeeWithType(EmployeeType.PARTTIMEEMPLOYEE));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWithInvalidEmployee() {
        EmployeeDao invalidemployee = new EmployeeDao(5L,
                5L,
                "invalidemployee",
                null,
                new BigDecimal("50000"));
        assertThatThrownBy(() -> employeeDAOAdapter.getEmployee(invalidemployee))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unsupported employee type");
    }
}