package com.example.thymeleaf.demo.controller;

import com.example.thymeleaf.demo.service.PdfService;
import com.lowagie.text.DocumentException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class PdfController {

    private final PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping(value = "/downLoad")
    public ResponseEntity<byte[]> downloadPdf(@RequestBody RequestTemplateParamsDto templateParams) throws IOException, DocumentException {

//        String htmlText = IOUtils.toString(file.getInputStream(), StandardCharsets.UTF_8);

        FileInputStream fileInputStream = new FileInputStream(new File("./src/main/resources/examplle.html"));
        String htmlText = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        pdfService.generatePdf(htmlText, outputStream, templateParams);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf; charset=UTF-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "KEK.pdf" + "\"")
                .body(outputStream.toByteArray());
    }
}
