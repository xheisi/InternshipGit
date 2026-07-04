package com.example.springbasics;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    public void logNotificationSent() {
        System.out.println("AUDIT: Notification was sent");
    }
}
