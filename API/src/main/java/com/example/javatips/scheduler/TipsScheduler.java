package com.example.javatips.scheduler;

import com.example.javatips.model.JavaTip;
import com.example.javatips.model.Subscriber;
import com.example.javatips.repository.JavaTipRepository;
import com.example.javatips.repository.SubscriberRepository;
import com.example.javatips.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class TipsScheduler {

    @Autowired
    private JavaTipRepository javaTipRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private EmailService emailService;

//    @Scheduled(fixedRate = 1000)
    @Scheduled(cron = "0 0 9 * * *")
    public void sendDailyTips() {
        List<JavaTip> tips = javaTipRepository.findAll();
        List<Subscriber> subscribers = subscriberRepository.findAll();

        if (tips.isEmpty()) {
            log.warn("No Java tips available to send.");
            return; // No tips to send
        }

        if (subscribers.isEmpty()) {
            log.warn("No subscribers available to send tips to.");
            return; // No subscribers to send to
        }


        Random random = new Random();
        JavaTip randomTip = tips.get(random.nextInt(tips.size()));

        for (Subscriber subscriber : subscribers) {
            try {
                emailService.sendDailyTipEmail(subscriber.getEmail(), randomTip.getTip());
                log.info("Successfully sent daily tip email to: {}", subscriber.getEmail());
            } catch (MessagingException e) {
                log.error("Failed to send daily tip email to: {}", subscriber.getEmail(), e);
                e.printStackTrace(); // Handle the exception accordingly
            }
        }
    }
}

