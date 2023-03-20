package com.example.gotogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GotogetherApplication {

    public static void main(String[] args) {
        System.setProperty("user.timezone", "Asia/Seoul");
        SpringApplication.run(GotogetherApplication.class, args);
    }

}
