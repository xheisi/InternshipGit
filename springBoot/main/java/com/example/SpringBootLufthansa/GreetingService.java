package com.example.SpringBootLufthansa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    //java -jar SpringBootLufthansa-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev/prod


    //@Value("${app.greeting:Hello}")
    //private String greeting;
    private final GreetingProperties greetingProperties;

    public GreetingService(GreetingProperties greetingProperties) {
        this.greetingProperties = greetingProperties;
    }
    public String greet(String name) {
        return greeting + " " + name;
    }

}
