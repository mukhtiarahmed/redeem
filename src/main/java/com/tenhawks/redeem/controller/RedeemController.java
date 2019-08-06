package com.tenhawks.redeem.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.tenhawks.redeem.service.RedeemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ahmed on 8/6/2019.
 */
@Controller
@RequestMapping("/redeem")
public class RedeemController {

    private static Logger log = LoggerFactory.getLogger(RedeemController.class);

    @Autowired
    private RedeemService redeemService;

    @PostConstruct
    public void init() {
        Assert.notNull(redeemService, "redeemService must not be null");
    }

    @RequestMapping("/excel/{fileName:.+}")
    public void downloadRedeemTokenExcelFile(
                 HttpServletResponse response, @PathVariable("fileName") String fileName){
        log.debug("Excel fileName : {}", fileName);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=" + fileName);
        try {
            redeemService.downloadRedeemExcelFile(response.getOutputStream());
        } catch (IOException e) {
            log.error("error at : ", e);
        }


    }

    @RequestMapping("/csv/{fileName:.+}")
    public void downloadRedeemTokenCSVFile(
                HttpServletResponse response, @PathVariable("fileName") String fileName){
        log.debug("CSV fileName : {}", fileName);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=" + fileName);
        try {
            redeemService.downloadRedeemCSVFile(response.getOutputStream());
        } catch (IOException e) {
            log.error("error at : ", e);
        }
    }
}
