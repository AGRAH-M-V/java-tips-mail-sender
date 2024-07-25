package com.example.javatips.exception;

public class TipNotFoundException extends RuntimeException {
    public TipNotFoundException(String message) {
        super(message);
    }
}
