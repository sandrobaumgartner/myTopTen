package com.example.mytopten;

import com.example.mytopten.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
//@ImportAutoConfiguration(classes = WebSecurityConfig.class)
//@ComponentScan(basePackages = {
//        "com.example.mytopten.jparepositories",
//        "com.example.mytopten.security.services",
//        "com.example.mytopten.security.jwt"})
public class MyTopTenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTopTenApplication.class, args);
    }
}
