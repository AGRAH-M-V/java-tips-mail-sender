package com.example.javatips.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SubscriptionRequest {
    @NotEmpty
    @Email
    private String email;
}
