package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class My_BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(My_BlogApplication.class,args);
    }
}
