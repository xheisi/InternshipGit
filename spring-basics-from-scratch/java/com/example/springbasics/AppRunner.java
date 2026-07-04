package com.example.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final NotificationManager notificationManager;
    private final ApplicationContext context;

    @Value("#{systemProperties['user.name']}")
    private String currentUser;

    @Autowired
    public AppRunner(NotificationManager notificationManager, ApplicationContext context) {
        this.notificationManager = notificationManager;
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application started by: " + currentUser);

        notificationManager.notifyUser("Welcome to Spring!");

        ConsoleNotificationService instance1 = context.getBean(ConsoleNotificationService.class);
        ConsoleNotificationService instance2 = context.getBean(ConsoleNotificationService.class);
        System.out.println("Bean instances are the same: " + (instance1 == instance2));
    }
}