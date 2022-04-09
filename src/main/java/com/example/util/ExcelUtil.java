package com.example.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExcelUtil {

    /**
     * @Param path 读取的路径
     * @return
     */
    public static void readContent(String path) throws IOException {
        File file = new File(path);

        InputStream input = new FileInputStream(file);
        Workbook sheets = new XSSFWorkbook(input);
        Sheet sheetAt = sheets.getSheetAt(0);
        // 获取真实的行数！！
        for (int i = 1; i < sheetAt.getPhysicalNumberOfRows()-1; i++) {
            // index = 1start
            Row row = sheetAt.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                String string = cell.getRichStringCellValue().getString();
                System.out.println(string);
            }
            System.out.println("=================================================");
        }
    }
}
