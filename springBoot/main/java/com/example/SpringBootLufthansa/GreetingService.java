package com.example.SpringBootLufthansa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Value("${app.greeting:Hii}")
    private String greeting;

    public String greet(String name) {
        return greeting + " " + name;
    }

}