package com.example.demo.employee.read;

import com.example.demo.employee.models.EmployeeDTO;
import com.example.demo.employee.models.employee.EmployeeType;
import com.example.demo.employee.models.salary.ContractorSalary;
import com.example.demo.employee.models.salary.FullTimeSalary;
import com.example.demo.employee.models.salary.PartTimeSalary;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExcelServiceTest {

    ExcelService excelService;

    @BeforeEach
    void init() {
        excelService = new ExcelService();
    }


    @Test
    void checkHeaderRows() throws IOException {
        List<String> headerNames = new ArrayList<>();
        try (XSSFWorkbook workbookContents = excelService.createWorkbookContents(
                List.of(
                        getEmployeeDTOWithType(EmployeeType.CONTRACTOR),
                        getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE),
                        getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE)
                )
        )) {
            XSSFSheet sheet = workbookContents.getSheet("employees");
            XSSFRow headerRow = sheet.getRow(0);
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                XSSFCell cell = headerRow.getCell(i);
                headerNames.add(cell.getStringCellValue());
            }
        }

        assertThat(headerNames).contains(
                "employeeId",
                "employeeType",
                "workingHoursPerDay",
                "baseSalary",
                "totalAnnualSalary",
                "stockCount");
    }

    @Test
    void containsCorrectNumberOfRows() throws IOException {
        List<EmployeeDTO> employeeDTOs = List.of(
                getEmployeeDTOWithType(EmployeeType.CONTRACTOR),
                getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE),
                getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE)
        );
        try (XSSFWorkbook workbookContents = excelService.createWorkbookContents(employeeDTOs)) {
            XSSFSheet sheet = workbookContents.getSheet("employees");
            assertThat(sheet.getLastRowNum()).isEqualTo(employeeDTOs.size());
        }
    }

    @Test
    void containsCorrectContents() throws IOException {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<EmployeeDTO> employeeDTOs = List.of(
                getEmployeeDTOWithType(EmployeeType.CONTRACTOR),
                getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE),
                getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE)
        );
        try (XSSFWorkbook workbookContents = excelService.createWorkbookContents(employeeDTOs)) {
            XSSFSheet sheet = workbookContents.getSheet("employees");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow currentRow = sheet.getRow(i);

                extractEmployeeDTOFromSheet(currentRow, employeeDTOList);
            }
        }

        assertThat(employeeDTOList).containsExactlyElementsOf(employeeDTOs);
    }

    @Test
    void testContentsWithExcelFile() throws IOException {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<EmployeeDTO> employeeDTOs = List.of(
                getEmployeeDTOWithType(EmployeeType.CONTRACTOR),
                getEmployeeDTOWithType(EmployeeType.FULLTIMEEMPLOYEE),
                getEmployeeDTOWithType(EmployeeType.PARTTIMEEMPLOYEE)
        );


        ByteArrayOutputStream excelFileFromExportData = excelService.createExcelFileFromExportData(employeeDTOs);
        Path tempFile = Files.createTempFile("myfile", ".xlsx");
        try(FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
            excelFileFromExportData.writeTo(fos);
        }
        try (InputStream is = new FileInputStream(tempFile.toFile())) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow.getPhysicalNumberOfCells() == headerRow.getPhysicalNumberOfCells()) {
                    extractEmployeeDTOFromSheet(currentRow, employeeDTOList);
                }
            }
        }
        Files.delete(tempFile);
        assertThat(employeeDTOList).containsExactlyElementsOf(employeeDTOs);
    }

    private void extractEmployeeDTOFromSheet(Row currentRow, List<EmployeeDTO> employeeDTOList) {
        Integer workingHoursPerDay = extractCellValue(currentRow.getCell(2), "int") == null ?
                null : (int) extractCellValue(currentRow.getCell(2), "int");
        BigDecimal baseSalary = extractCellValue(currentRow.getCell(3)) == null ?
                null : new BigDecimal(String.valueOf(extractCellValue(currentRow.getCell(3))));
        Integer stockCount = extractCellValue(currentRow.getCell(5), "int") == null ?
                null : (int) extractCellValue(currentRow.getCell(5), "int");

        EmployeeDTO employeeDTO = new EmployeeDTO((long) extractCellValue(currentRow.getCell(0)),
                (String) extractCellValue(currentRow.getCell(1)),
                workingHoursPerDay,
                baseSalary,
                new BigDecimal(String.valueOf(extractCellValue(currentRow.getCell(4)))),
                stockCount);
        employeeDTOList.add(employeeDTO);
    }

    private Object extractCellValue(Cell currentCell, String... returnType) {
        switch (currentCell.getCellType()) {
            case NUMERIC -> {
                double numericCellValue = currentCell.getNumericCellValue();
                if (returnType.length > 0 && returnType[0].equals("int")) {
                    return (int) numericCellValue;
                } else {
                    return (long) numericCellValue;
                }
            }
            case STRING -> {
                return currentCell.getStringCellValue();
            }
            default -> {
                return null;
            }
        }
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