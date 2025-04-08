package com.example.demo.employee.read;

import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.employee.models.salary.ContractorSalary;
import com.example.demo.employee.models.salary.FullTimeSalary;
import com.example.demo.employee.models.salary.PartTimeSalary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(ReadEmployeeController.class)
class ReadEmployeeControllerTest {

    @Autowired
    MockMvcTester mockMvcTester;

    @MockitoBean
    ReadEmployeeService readEmployeeService;

    @MockitoBean
    ExcelService excelService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldGetEmployeeWithId() throws JsonProcessingException {
        EmployeeDTO employeeDTO = new EmployeeDTO(1L,
                EmployeeType.CONTRACTOR.getValue(),
                null,
                new BigDecimal("50000"),
                new ContractorSalary(new BigDecimal("50000")).computeAnnualSalary(),
                0
        );
        when(readEmployeeService.getEmployee(anyLong())).thenReturn(employeeDTO);

        MvcTestResult result = mockMvcTester.get().uri("/v1/api/employees/{id}", 1)
                .exchange();

        System.out.println(result.getResponse());
        assertThat(result).hasStatusOk()
                .bodyJson().isEqualTo(objectMapper.writeValueAsString(employeeDTO));
    }

    @Test
    void shouldGetAllEmployeeAsJson() throws JsonProcessingException {
        List<EmployeeDTO> expectedEmployeeDTOList = List.of(
                getEmployeeDTOWithType(EmployeeType.CONTRACTOR),
                getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE),
                getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE)
        );
        when(readEmployeeService.getAllEmployeesAsExport()).thenReturn(expectedEmployeeDTOList);
        MvcTestResult result = mockMvcTester.get().uri("/v1/api/employees")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .exchange();

        assertThat(result).hasStatusOk()
                .bodyJson().isEqualTo(objectMapper.writeValueAsString(expectedEmployeeDTOList));
    }

    @Test
    void shouldGetAllEmployeesAsExcel() throws IOException {
        List<EmployeeDTO> expectedEmployeeDTOList = List.of(
                getEmployeeDTOWithType(EmployeeType.CONTRACTOR),
                getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE),
                getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE)
        );
        when(readEmployeeService.getAllEmployeesAsExport()).thenReturn(expectedEmployeeDTOList);
        when(excelService.createExcelFileFromExportData(any())).thenReturn(getFileByteArrayOutputStream());
        MvcTestResult result = mockMvcTester.get().uri("/v1/api/employees")
                .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .exchange();

        assertThat(result).hasStatusOk()
                .body()
                .containsExactly(getFileByteArrayOutputStream().toByteArray());

    }

    private ByteArrayOutputStream getFileByteArrayOutputStream() throws IOException {
        ByteArrayOutputStream baos;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("myfile.xlsx")) {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        }
        return baos;
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