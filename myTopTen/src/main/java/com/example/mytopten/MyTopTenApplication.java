package com.example.mytopten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MyTopTenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTopTenApplication.class, args);
    }

}
