package com.utils;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {

    XSSFWorkbook workbook;
    XSSFSheet sheet;
    public static Logger log = Logger.getLogger(ExcelUtil.class);

    public Map<String, String> getTestData(int rowNum) throws IOException {
        Map<String, String> data = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("Testdata.xlsx");
        FileInputStream fs = new FileInputStream(new File(resource.getFile()));
//Creating a workbook
        this.workbook = new XSSFWorkbook(fs);
        this.sheet = workbook.getSheet("DATA");
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(0);
        //Get the username in a variable
        String un = cell.getStringCellValue() + RandomUtils.nextInt() + "@gmail.com";
        log.info("user name " + rowNum + "is " + un);
        data.put("username", un);
        Cell cell2 = row.getCell(1);
        //Get the password in a variable
        String pwd = cell2.getStringCellValue();
        data.put("password", pwd);
        log.info("password " + rowNum + "is " + pwd);
        fs.close();
        return data;
    }

    public void writeStatus(String status, int rowNum) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("Testdata.xlsx");
        FileInputStream fs = new FileInputStream(new File(resource.getFile()));
        this.workbook = new XSSFWorkbook(fs);
        this.sheet = workbook.getSheet("DATA");
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(8);
        cell.setCellValue(status);
    }

    //
    public static void main(String[] strings) throws IOException {
        ExcelUtil xl = new ExcelUtil();
        Map<String, String> map = xl.getTestData(1);
        xl.writeStatus("PASS", 1);
    }

}
