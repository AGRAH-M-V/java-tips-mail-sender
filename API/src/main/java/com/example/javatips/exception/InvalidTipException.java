package com.example.javatips.exception;

public class InvalidTipException extends RuntimeException {
    public InvalidTipException(String message) {
        super(message);
    }
}
