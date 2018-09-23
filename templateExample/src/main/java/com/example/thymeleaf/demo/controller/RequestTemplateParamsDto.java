package com.example.thymeleaf.demo.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestTemplateParamsDto {
    private List<FirstTable> firstTableParams;
    private List<SecondTable> secondTableParams;

    @Getter
    @Setter
    @NoArgsConstructor
    private static class FirstTable {
        private String name;
        private String date;
        private String example;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class SecondTable {
        private String name;
        private String date;
        private String example;
    }
}
