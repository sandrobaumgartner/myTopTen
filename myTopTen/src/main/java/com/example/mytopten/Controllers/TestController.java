package com.example.mytopten.Controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/who/{name}")
    public String whoShotFirst(@PathVariable String name) {
        return name + " shot first";
    }

    @PostMapping("/who")
    public String whoShotFirst2(@RequestBody String name) {
        return name + " shot first";
    }
}
