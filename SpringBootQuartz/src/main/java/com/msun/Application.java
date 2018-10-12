package com.msun;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@DisallowConcurrentExecution
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
