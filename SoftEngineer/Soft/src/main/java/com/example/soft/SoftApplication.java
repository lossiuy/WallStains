package com.example.soft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SoftApplication {


    public static void main(String[] args) {
        SpringApplication.run(SoftApplication.class, args);
    }

}
