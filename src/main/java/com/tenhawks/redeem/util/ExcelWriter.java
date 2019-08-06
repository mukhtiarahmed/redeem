package com.tenhawks.redeem.util;

/**
 * Created by Ahmed on 8/1/2019.
 */
import com.tenhawks.redeem.model.Redeem;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

public class ExcelWriter {


    private static Logger log = LoggerFactory.getLogger(ExcelWriter.class);


    public static XSSFWorkbook getWorkbook(List<Redeem> redeemList, String sheetName) {
        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet(sheetName);

        // This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<>();
        data.put("1", new Object[] { "Id", "Redeem Token", "Date", "User"});
        int count = 1;
        for(Redeem redeem : redeemList) {
            data.put(String.valueOf(++count), new Object[] {redeem.getId() , redeem.getRedeemToken(),
                    Utils.converDateToString(redeem.getRedeemTime()), Utils.removeNull(redeem.getRedeemUser())});
        }

        // Iterate over data and write to sheet
        Set<String> keySet = data.keySet();
        int rowNum = 0;
        for (String key : keySet) {
            Row row = sheet.createRow(rowNum++);
            Object[] objArr = data.get(key);
            int cellNum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellNum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }

        return workbook;
    }

    public static byte[] excelInputStream(List<Redeem> redeemList, String sheetName) {

        XSSFWorkbook workbook = getWorkbook(redeemList, sheetName);
        try {

            // A ByteArrayOutputStream holds the content in memory
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            log.error("error at : ", e);
        }
        return null;
    }

    public static void excelInputStream(List<Redeem> redeemList, String sheetName, OutputStream outputStream) {

        try {
            XSSFWorkbook workbook = getWorkbook(redeemList, sheetName);
            workbook.write(outputStream);
        } catch (Exception e) {
            log.error("error at : ", e);
        }

    }

}
