package com.kiyozawa.houses;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HousesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousesApplication.class, args);
    }

}

