package com.tinqinacademy.bffservice.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.tinqinacademy.bffservice")
public class BffServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffServiceApplication.class, args);
    }

}
