package com.example.javatips.model;

import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "subscribers")
public class Subscriber {
    @Id
    private String id;
    @Email
    private String email;
}
