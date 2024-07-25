package com.example.javatips.service;

import com.example.javatips.dto.SubscriptionRequest;
import com.example.javatips.exception.SubscriberAlreadyExistsException;
import com.example.javatips.model.Subscriber;
import com.example.javatips.repository.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubscriptionService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Subscriber subscribe(SubscriptionRequest request) {
        log.info("Attempting to subscribe email: {}", request.getEmail());
        // Check if subscriber already exists
        Subscriber existingSubscriber = subscriberRepository.findByEmail(request.getEmail());
        if (existingSubscriber != null) {
            // Throw custom exception if subscriber already exists
            log.warn("Subscriber with email {} already exists!", request.getEmail());
            throw new SubscriberAlreadyExistsException("Subscriber with email " + request.getEmail() + " already exists!");
        }

        // If subscriber does not exist, create a new one
        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setEmail(request.getEmail());
        Subscriber savedSubscriber = subscriberRepository.save(newSubscriber);
        log.info("New subscriber created with email: {}", savedSubscriber.getEmail());
        return savedSubscriber;
    }

}
