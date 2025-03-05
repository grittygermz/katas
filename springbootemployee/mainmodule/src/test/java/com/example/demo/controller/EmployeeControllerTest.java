package com.example.demo.controller;

import com.example.demo.exceptions.InvalidException;
import com.example.demo.models.employee.FullTimeEmployee;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ExcelService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    EmployeeService employeeService;

    @MockitoBean
    ExcelService excelService;

    @Test
    void shouldGetAnEmployeeIfPresent() throws Exception {
        long employeeId = 1L;

        when(employeeService.getEmployee(anyLong()))
                .thenReturn(new FullTimeEmployee(employeeId, new BigDecimal("10000"), 20));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(1))
                .andExpect(jsonPath("$.stocks").value(20))
                .andExpect(jsonPath("$.salary.baseSalary"). value(10000));
    }

    @Test
    void shouldHaveErrorIfEmployeeIsAbsent() throws Exception {
        when(employeeService.getEmployee(anyLong()))
                .thenThrow(new InvalidException("employee with id 1 does not exists"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("employee with id 1 does not exists"));
    }

}