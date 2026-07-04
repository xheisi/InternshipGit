package com.example.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    private final NotificationService ns;
    private AuditService auditService;

    //   public NotificationManager(@Qualifier("console") NotificationService ns){
    @Autowired
    public NotificationManager(@Qualifier("email") NotificationService ns){
        this.ns = ns;
    }

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void notifyUser(String message){
        ns.send(message);
        auditService.logNotificationSent();
    }
}