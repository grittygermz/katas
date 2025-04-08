package com.example.demo.employee.read;

import com.example.demo.employee.models.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("v1/api/employees")
public class ReadEmployeeController {

    private final ReadEmployeeService readEmployeeService;
    private final ExcelService excelService;

    public ReadEmployeeController(ReadEmployeeService readEmployeeService, ExcelService excelService) {
        this.readEmployeeService = readEmployeeService;
        this.excelService = excelService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getAnEmployee(@PathVariable long id) {
        return readEmployeeService.getEmployee(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> getAllEmployees() {
        return readEmployeeService.getAllEmployeesAsExport();
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> getAllEmployeesAsExcel() {
        List<EmployeeDTO> allEmployees = readEmployeeService.getAllEmployeesAsExport();
        ByteArrayOutputStream excelFileFromExportData = excelService.createExcelFileFromExportData(allEmployees);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        httpHeaders.setContentDisposition(ContentDisposition.builder("attachment").filename("employees.xlsx").build());

        InputStreamResource resource = new InputStreamResource(
                new ByteArrayInputStream(excelFileFromExportData.toByteArray())
        );
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }

    //@GetMapping(value = "/export-queue" )
    //public ResponseEntity<InputStreamResource> getAllEmployeesAsExcelAsync() {
    //    return null;
    //}
    //
    //@GetMapping(value = "/export/status/{jobId}" )
    //public ResponseEntity<InputStreamResource> getJobStatus(@PathVariable String jobId) {
    //    return null;
    //}
    //
    //@GetMapping(value = "/export/download/{jobId}" )
    //public ResponseEntity<InputStreamResource> getJobOutput(@PathVariable String jobId) {
    //    return null;
    //}


}
