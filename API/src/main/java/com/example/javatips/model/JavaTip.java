package com.example.javatips.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "javaTips")
public class JavaTip {
    @Id
    private String id;
    private String tip;
}
