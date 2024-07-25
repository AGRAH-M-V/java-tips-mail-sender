package com.example.javatips.controller;

import com.example.javatips.dto.JavaTipDTO;
import com.example.javatips.service.TipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tips")
public class TipsController {

    @Autowired
    private TipsService tipsService;

    @PostMapping("/add")
    public ResponseEntity<JavaTipDTO> addTip(@Valid @RequestBody JavaTipDTO javaTipDTO) {
        JavaTipDTO savedTipDTO = tipsService.addTip(javaTipDTO);
        return ResponseEntity.ok(savedTipDTO);
    }

    @PostMapping("/bulk-add")
    public ResponseEntity<List<JavaTipDTO>> addTips(@Valid @RequestBody List<JavaTipDTO> tipsDTO) {
        List<JavaTipDTO> savedTipsDTO = tipsService.addTips(tipsDTO);
        return ResponseEntity.ok(savedTipsDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JavaTipDTO>> getAllTips() {
        List<JavaTipDTO> tipsDTO = tipsService.getAllTips();
        return ResponseEntity.ok(tipsDTO);
    }
}
