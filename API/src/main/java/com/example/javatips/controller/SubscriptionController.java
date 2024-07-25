package com.example.javatips.controller;

import com.example.javatips.dto.SubscriptionRequest;
import com.example.javatips.model.Subscriber;
import com.example.javatips.service.EmailService;
import com.example.javatips.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/subscribe")
    public ResponseEntity<Subscriber> subscribe(@Validated @RequestBody SubscriptionRequest request) {
        Subscriber subscriber = subscriptionService.subscribe(request);
        return ResponseEntity.ok(subscriber);
    }

    @PostMapping("/send-daily-tips")
    public ResponseEntity<String> sendDailyTipsManually() {
        emailService.sendDailyTipsEmail();
        return ResponseEntity.ok("Daily tips email sending initiated successfully.");
    }

}

