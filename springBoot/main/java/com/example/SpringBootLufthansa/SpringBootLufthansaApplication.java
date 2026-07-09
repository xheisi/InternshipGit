package com.example.SpringBootLufthansa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GreetingProperties.class)
public class SpringBootLufthansaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLufthansaApplication.class, args);
	}
		//RUN: mvn spring-boot:run
}
