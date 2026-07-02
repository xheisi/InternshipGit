package com.example.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private final NotificationService ns;

    @Autowired
    public NotificationManager(NotificationService ns){
        this.ns = ns;
    }
    public void notifyUser(String message){
        System.out.println("message");
    }
}
