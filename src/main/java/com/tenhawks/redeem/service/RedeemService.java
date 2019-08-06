package com.tenhawks.redeem.service;

import com.tenhawks.redeem.model.Redeem;
import com.tenhawks.redeem.repository.RedeemRepository;
import com.tenhawks.redeem.util.Utils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tenhawks.redeem.util.Utils.CSV_FILE_NAME;
import static com.tenhawks.redeem.util.Utils.EXCEL_FILE_NAME;
import static com.tenhawks.redeem.util.Utils.EXCEL_SHEET_NAME;

/**
 * Created by Ahmed on 7/31/2019.
 */
@Service
@Transactional
public class RedeemService {


    private static Logger log = LoggerFactory.getLogger(RedeemService.class);

    @Value("${total.token}")
    private int totalToken;

    @Autowired
    RedeemRepository redeemRepository;

    private long count;

    @PostConstruct
    public void init() {
        Assert.notNull(redeemRepository, "redeemRepository must not be null");
        count = redeemRepository.count();
        if(count < totalToken) {
            generateToken();
        }

        try {
            Utils.createCSVFile(redeemRepository.findAll(), CSV_FILE_NAME);
            Utils.createExcelFile(redeemRepository.findAll(), EXCEL_SHEET_NAME, EXCEL_FILE_NAME);
        } catch (Exception e) {
            log.error("error at : ", e);
        }

    }

    public void generateToken() {
        Redeem redeem;
        long remain = totalToken - count;
        List<Redeem> redeemList = new ArrayList<>();
        for(int i=0; i< remain; i++) {
            redeem = new Redeem();
            String token =Utils.getRandomString(12);
            redeem.setRedeemToken(token);
            redeemList.add(redeem);
            if(redeemList.size() == 100) {
                redeemRepository.saveAll(redeemList);
                redeemList.clear();
            }
        }
        if(!redeemList.isEmpty()) {
            redeemRepository.saveAll(redeemList);
            redeemList.clear();
        }
    }

    public void downloadRedeemExcelFile(OutputStream outputStream) throws IOException {
        Path path = Paths.get(System.getProperty("user.dir"));
        File download = path.toFile();
        File file = new  File(download, EXCEL_FILE_NAME);
        IOUtil.copyCompletely(new FileInputStream(file), outputStream);
    }

    public void downloadRedeemCSVFile(OutputStream outputStream) throws IOException {
        Path path = Paths.get(System.getProperty("user.dir"));
        File download = path.toFile();
        File file = new  File(download, CSV_FILE_NAME);
        IOUtil.copyCompletely(new FileInputStream(file), outputStream);
    }





}
