package com.saucedemo.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;

public class ExcelUtils {
    public static Object[][] getTestData(String sheetName) {
        Object[][] data = null;
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/testdata.xlsx");
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();

            data = new Object[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
