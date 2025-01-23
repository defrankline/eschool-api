package com.kachinga.eschool;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        startSuccess();
    }

    private static void startSuccess() {
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BOLD = "\u001B[1m";
        final String ANSI_RESET = "\u001B[0m";
        System.out.println(ANSI_GREEN + ANSI_BOLD + "SCHOOL API STARTED SUCCESSFULLY" + ANSI_RESET);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
    }

}
