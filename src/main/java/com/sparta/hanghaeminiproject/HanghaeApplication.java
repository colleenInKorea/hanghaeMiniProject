package com.sparta.hanghaeminiproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeApplication.class, args);
    }

}
