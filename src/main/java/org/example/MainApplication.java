package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.example")
public class MainApplication {
    public static void main(String[] args) {
        System.out.printf("Hello Spring Boot!");
        SpringApplication.run(MainApplication.class,args);
    }
}