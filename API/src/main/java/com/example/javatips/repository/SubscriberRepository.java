package com.example.javatips.repository;

import com.example.javatips.model.Subscriber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends MongoRepository<Subscriber, String> {
    Subscriber findByEmail(String email);
}
