package com.example.springbasics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService{

    @Value("${notification.prefix}")
    private String notificationPrefix;

    @Override
    public void send(String message) {
        System.out.println(message);
    }
}
