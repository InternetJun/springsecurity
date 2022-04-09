package com.example.leetcode;

import com.alibaba.fastjson.JSONObject;
import com.example.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

import java.io.*;

public class JsonTest {
    @Test
    public void main() {
        String s = "{\"2\":\"efg\",\"1\":\"abc\"}";
        JSONObject jsonObject = JSONObject.parseObject(s);
        System.out.println(jsonObject);
    }

    @Test
    /*创建一个excel的内容*/
    public void main1() throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();

        FileOutputStream fileOut = new FileOutputStream("workbook.xls");

        wb.write(fileOut);

        fileOut.close();
    }

    @Test
    public void readExcel() throws IOException {
        String path = "C:\\Users\\Administrator\\Desktop\\address.xlsx";
        ExcelUtil.readContent(path);
    }
    
    @Test
    /*要是数据量大的时候呢？*/
    public void mainReadTxt() throws IOException {
        FileReader fileReader = new FileReader("C:\\Users\\Administrator\\Desktop\\常用软件\\project\\security\\src\\main\\java\\com\\example\\leetcode\\test");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            String s = line.replaceAll("(?<=,|，)([\\s]+|[\\s]{1})", "");
            String s1 = s.replaceAll("[\\s]+", " ");
            System.out.println(s1);
            String[] strings = new String[4];
            int left = 0, right = 0;
            while(right < line.length()) {
            }
        }
    }
}
