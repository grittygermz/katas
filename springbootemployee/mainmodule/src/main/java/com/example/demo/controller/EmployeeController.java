package com.example.demo.controller;

import com.example.demo.domain.AddEmployeePayload;
import com.example.demo.entity.EmployeeDao;
import com.example.demo.models.EmployeeExport;
import com.example.demo.models.employee.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ExcelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ExcelService excelService;

    public EmployeeController(EmployeeService employeeService, ExcelService excelService) {
        this.employeeService = employeeService;
        this.excelService = excelService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Employee> getOneEmployee(@PathVariable long id) {
        Employee employee = employeeService.getEmployee(id);
        log.info("{}", employee);
        return ResponseEntity.ok(employee);
    }

    @PostMapping(value = "/addEmployee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOneEmployee(@Valid @RequestBody AddEmployeePayload addEmployeePayload) {
        employeeService.addEmployee(addEmployeePayload);
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee successfully added");
    }

    @PutMapping(value = "/updateEmployee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOneEmployee(@Valid @RequestBody AddEmployeePayload addEmployeePayload, @PathVariable long id) {
        employeeService.updateEmployee(id, addEmployeePayload);
        return ResponseEntity.ok("Employee successfully updated");
    }

    @DeleteMapping(value = "/deleteEmployee/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteOneEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee successfully deleted");
    }

    @GetMapping(value = "/allEmployees", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<EmployeeExport>> getAllEmployees() {
        List<EmployeeExport> allEmployees = employeeService.getAllEmployeesAsExport();
        log.info("{}", allEmployees);
        return ResponseEntity.ok(allEmployees);
    }

    @GetMapping(value = "/file/allEmployees" )
    public ResponseEntity<InputStreamResource> getAllEmployeesAsExcel() {
        List<EmployeeExport> allEmployees = employeeService.getAllEmployeesAsExport();
        ByteArrayOutputStream excelFileFromExportData = excelService.createExcelFileFromExportData(allEmployees);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(excelFileFromExportData.toByteArray());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        httpHeaders.setContentDisposition(ContentDisposition.builder("attachment").filename("myfile.xlsx").build());

        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }
}
