package com.example.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final NotificationManager notificationManager;
    @Autowired
    public AppRunner(NotificationManager notificationManager){
        this.notificationManager = notificationManager;
    }

    @Override
    public void run(String... args) throws Exception {
        notificationManager.notifyUser("Welcome to Spring!");
    }
}
