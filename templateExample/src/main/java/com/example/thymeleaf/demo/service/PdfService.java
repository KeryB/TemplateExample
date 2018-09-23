package com.example.thymeleaf.demo.service;

import com.example.thymeleaf.demo.controller.RequestTemplateParamsDto;
import com.lowagie.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface PdfService {

    void generatePdf(String htmlText, ByteArrayOutputStream fileName, RequestTemplateParamsDto templateParams) throws DocumentException, IOException;
}
