package com.example.springbasics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("console")
@Scope("prototype")

public class ConsoleNotificationService implements NotificationService {

    @Value("${notification.prefix}")
    private String notificationPrefix;

    @Override
    public void send(String message) {
        System.out.println(notificationPrefix + " Sending CONSOLE notification: " + message);
    }
}