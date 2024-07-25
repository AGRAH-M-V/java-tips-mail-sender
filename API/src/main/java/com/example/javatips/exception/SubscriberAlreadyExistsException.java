package com.example.javatips.exception;

public class SubscriberAlreadyExistsException extends RuntimeException {
    public SubscriberAlreadyExistsException(String message) {
        super(message);
    }
}
