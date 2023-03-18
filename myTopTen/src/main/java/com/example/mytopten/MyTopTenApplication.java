package com.example.mytopten;

import com.example.mytopten.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
public class MyTopTenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTopTenApplication.class, args);
    }
}
