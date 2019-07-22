package com.dld.checkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CheckInApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckInApplication.class, args);
    }

}
