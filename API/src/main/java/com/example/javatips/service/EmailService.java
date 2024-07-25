package com.example.javatips.service;

import com.example.javatips.model.JavaTip;
import com.example.javatips.model.Subscriber;
import com.example.javatips.repository.JavaTipRepository;
import com.example.javatips.repository.SubscriberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Random;


@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaTipRepository javaTipRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    public void sendDailyTipsEmail() {
        List<JavaTip> tips = javaTipRepository.findAll();
        List<Subscriber> subscribers = subscriberRepository.findAll();

        if (tips.isEmpty()) {
            log.warn("No Java tips available to send.");
            return;
        }

        if (subscribers.isEmpty()) {
            log.warn("No subscribers available to send tips to.");
            return;
        }

        Random random = new Random();
        JavaTip randomTip = tips.get(random.nextInt(tips.size()));

        for (Subscriber subscriber : subscribers) {
            try {
                sendDailyTipEmail(subscriber.getEmail(), randomTip.getTip());
                log.info("Successfully sent daily tip email to: {}", subscriber.getEmail());
            } catch (MessagingException e) {
                log.error("Failed to send daily tip email to: {}", subscriber.getEmail(), e);
            }
        }
    }

    public void sendDailyTipEmail(String to, String tip) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("tip", tip);
        String html = templateEngine.process("dailyTipEmail", context);

        helper.setTo(to);
        helper.setSubject("Java Tip of the Day");
        helper.setText(html, true);

        mailSender.send(message);
        log.info("Email sent to {} with tip: {}", to, tip);
    }
}
