package com.example.javatips.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class JavaTipDTO {
    private String id;
    @NotEmpty
    private String tip;
}
