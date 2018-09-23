package com.example.thymeleaf.demo.service;

import com.example.thymeleaf.demo.controller.RequestTemplateParamsDto;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
public class PdfServiceImpl implements PdfService {

    private final TemplateEngine templateEngine;
    private static final String UTF_8 = "UTF-8";

    @Autowired
    public PdfServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @PostConstruct
    public void init() {
        final StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setOrder(3);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateEngine.addTemplateResolver(templateResolver);
    }

    @Override
    public void generatePdf(String htmlText, ByteArrayOutputStream outputStream, RequestTemplateParamsDto templateParams) throws DocumentException, IOException {
        Context context = new Context();
        context.setVariable("templateParams", templateParams);
        String processedHtml = templateEngine.process(htmlText, context);
        String xHtml = convertToXhtml(processedHtml);

        ITextRenderer renderer = new ITextRenderer();

        renderer.setDocumentFromString(xHtml);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();
        outputStream.close();
    }


    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }


}
