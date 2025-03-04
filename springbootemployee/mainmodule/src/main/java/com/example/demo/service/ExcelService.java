package com.example.demo.service;

import com.example.demo.models.EmployeeExport;
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

    public ByteArrayOutputStream createExcelFileFromExportData(List<EmployeeExport> employeeExportList) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet("sheet1");

            createHeaderRow(sheet);
            for (int i = 0; i < employeeExportList.size(); i++) {
                EmployeeExport employeeExport = employeeExportList.get(i);
                createContentRow(sheet, i + 1, employeeExport);
            }
            resizeColumn(sheet);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream;
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void resizeColumn(XSSFSheet sheet) {
        for (int i = 0; i < EmployeeExport.class.getDeclaredFields().length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createContentRow(XSSFSheet sheet, int i, EmployeeExport employeeExport) throws IllegalAccessException {
        XSSFRow row = sheet.createRow(i);
        Class<?> objClass = employeeExport.getClass();
        Field[] fields = objClass.getDeclaredFields();

        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            Object value = fields[j].get(employeeExport);
            addValueToCellInRow(row, j, value);
        }
    }

    private void createHeaderRow(XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        Field[] fields = EmployeeExport.class.getDeclaredFields();
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
