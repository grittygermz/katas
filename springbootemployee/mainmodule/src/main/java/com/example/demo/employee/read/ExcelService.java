package com.example.demo.employee.read;

import com.example.demo.employee.models.EmployeeDTO;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ExcelService {

    public ByteArrayOutputStream createExcelFileFromExportData(List<EmployeeDTO> employeeDTOList) {
        XSSFWorkbook workbookWithContents = createWorkbookContents(employeeDTOList);

        try {
            ByteArrayOutputStream byteArrayOutputStream = captureWorkBookContents(workbookWithContents);
            workbookWithContents.close();
            return byteArrayOutputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    XSSFWorkbook createWorkbookContents(List<EmployeeDTO> employeeDTOList) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("employees");

            createHeaderRow(sheet);
            createContent(employeeDTOList, sheet);
            resizeColumn(sheet);

            return workbook;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream captureWorkBookContents(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        return byteArrayOutputStream;
    }

    private void createContent(List<EmployeeDTO> employeeDTOList, XSSFSheet sheet) throws IllegalAccessException {
        for (int i = 0; i < employeeDTOList.size(); i++) {
            EmployeeDTO employeeDTO = employeeDTOList.get(i);
            createContentRow(sheet, i + 1, employeeDTO);
        }
    }

    private void resizeColumn(XSSFSheet sheet) {
        for (int i = 0; i < EmployeeDTO.class.getDeclaredFields().length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createContentRow(XSSFSheet sheet, int i, EmployeeDTO employeeDTO) throws IllegalAccessException {
        XSSFRow row = sheet.createRow(i);
        Class<?> objClass = employeeDTO.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            Object value = fields[j].get(employeeDTO);
            addValueToCellInRow(row, j, value);
        }
    }

    private void createHeaderRow(XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        Field[] fields = EmployeeDTO.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            addValueToCellInRow(row, i, fields[i].getName());
        }
    }

    private <T> void addValueToCellInRow(XSSFRow row, int columnIndex, T cellValue) {
        XSSFCell cell0 = row.createCell(columnIndex);
        if (cellValue instanceof String) {
            cell0.setCellValue((String) cellValue);
        } else if (cellValue instanceof Long) {
            cell0.setCellValue((Long) cellValue);
        } else if (cellValue instanceof Integer) {
            cell0.setCellValue((Integer) cellValue);
        } else if (cellValue instanceof BigDecimal castedCellValue) {
            cell0.setCellValue(castedCellValue.toString());
        }
    }
}
