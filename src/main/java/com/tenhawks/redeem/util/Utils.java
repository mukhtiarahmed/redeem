package com.tenhawks.redeem.util;

import com.tenhawks.redeem.model.Redeem;
import com.opencsv.*;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * Created by Ahmed on 8/1/2019.
 */
public class Utils {

    public static final String EXCEL_FILE_NAME = "redeem-token.xlsx";

    public static final String EXCEL_SHEET_NAME = "Redeem Token";

    public static final String CSV_FILE_NAME = "redeem-token.csv";

    public static String getUniqueId() {
        return randomUUID().toString().replaceAll("-", "");
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        try {
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String converDateToString(Date date) {
        if(date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy 'T'HH:mm:ssX");
        return sdf.format(date);

    }
    public static String objectToString(Object object) {
        if(object == null) return "";
        return object.toString();

    }

    public static String removeNull(String string) {
        if(string == null) return "";
        return string;

    }

    public static List<String[]> readAllDataAtOnce(String file, char seprator)
    {
        try {
            final CSVParser parser = new CSVParserBuilder().withSeparator(seprator).withIgnoreQuotations(false).build();
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1).withCSVParser(parser)
                    .build();
            List<String[]> domains =  csvReader.readAll();
            filereader.close();
            return domains;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    public static File createCSVFile(List<Redeem> redeemList , String fileName) throws Exception {

        // Creating writer class to generate
        // csv file
        Path path = Paths.get(System.getProperty("user.dir"));
        File download = path.toFile();
        File file = new  File(download, fileName);
        FileWriter writer = new
                FileWriter(file );
        // Create Mapping Strategy to arrange the
        // column name in order
        ColumnPositionMappingStrategy mappingStrategy=
                new ColumnPositionMappingStrategy();
        mappingStrategy.setType(Redeem.class);
        // Arrange column name as provided in below array.
        String[] columns = new String[]
                { "id", "redeemToken", "redeemTime", "redeemUser"};
        mappingStrategy.setColumnMapping(columns);

        writer.append("Id, Redeem Token, Redeem Date, User\n");

        // Creating StatefulBeanToCsv object
        StatefulBeanToCsvBuilder<Redeem> builder=
                new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER);
        StatefulBeanToCsv beanWriter =
                builder.withMappingStrategy(mappingStrategy).build();

        // Write list to StatefulBeanToCsv object
        beanWriter.write(redeemList);

        // closing the writer object
        writer.close();
        return file;
    }


    public static File createExcelFile(List<Redeem> redeemList, String sheetName, String fileName) {
        // Creating writer class to generate
        // excel file
        Path path = Paths.get(System.getProperty("user.dir"));
        File download = path.toFile();
        File file = new File(download, fileName);
        byte[] bytes = ExcelWriter.excelInputStream(redeemList, sheetName);
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(bytes);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String getRandomString(int length) {
        String random = UUID.randomUUID().toString();
        random = random.replaceAll("-", "");
        if (random.length() > length) {
            return random.substring(0, length);
        } else if(random.length() == length) {
            return random;
        }
        do {
            random = random.concat(random);
        } while (random.length() < length);

        if (random.length() > length) {
            return random.substring(0, length);
        } else {
            return random;
        }

    }

    public String  addRandomStringInFileName(String filename) {

        StringBuilder newFileName = new StringBuilder();
        filename = filename.replaceAll("\\s+" , "-");
        if (filename.contains(".")) {
            newFileName.append(filename.substring(0, filename.lastIndexOf('.')));
            newFileName.append('-');
            newFileName.append(randomUUID().toString().split("-")[0]);
            newFileName.append(filename.substring(filename.lastIndexOf('.')));
        } else {
            newFileName.append(filename);
            newFileName.append('-');
            newFileName.append(randomUUID().toString().split("-")[0]);
        }

        return newFileName.toString();

    }





}
