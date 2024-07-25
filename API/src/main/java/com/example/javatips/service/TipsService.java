package com.example.javatips.service;

import com.example.javatips.dto.JavaTipDTO;
import com.example.javatips.exception.InvalidTipException;
import com.example.javatips.exception.TipNotFoundException;
import com.example.javatips.model.JavaTip;
import com.example.javatips.repository.JavaTipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class TipsService {

    @Autowired
    private JavaTipRepository javaTipRepository;

    // Method to add a single tip
    public JavaTipDTO addTip(@Valid JavaTipDTO javaTipDTO) {
        log.info("Attempting to add a new tip: {}", javaTipDTO);

        if (javaTipDTO.getTip() == null || javaTipDTO.getTip().trim().isEmpty()) {
            log.error("Tip cannot be empty: {}", javaTipDTO);
            throw new InvalidTipException("Tip cannot be empty");
        }

        // Convert DTO to Model
        JavaTip javaTip = new JavaTip();
        javaTip.setTip(javaTipDTO.getTip());

        JavaTip savedTip = javaTipRepository.save(javaTip);
        JavaTipDTO savedTipDTO = new JavaTipDTO(savedTip.getId(), savedTip.getTip());
        log.info("Successfully added tip: {}", savedTipDTO);
        return savedTipDTO;
    }

    // Method to add multiple tips
    public List<JavaTipDTO> addTips(@Valid List<JavaTipDTO> tipsDTO) {
        log.info("Attempting to add multiple tips: {}", tipsDTO);

        // Check if the list is empty
        if (tipsDTO == null || tipsDTO.isEmpty()) {
            log.error("Cannot add an empty list of tips");
            throw new InvalidTipException("Cannot add an empty list of tips");
        }

        // Validate each tip
        for (JavaTipDTO tipDTO : tipsDTO) {
            if (tipDTO.getTip() == null || tipDTO.getTip().trim().isEmpty()) {
                log.error("Tip cannot be empty: {}", tipDTO);
                throw new InvalidTipException("Tip cannot be empty");
            }
        }

        // Convert DTOs to Models
        List<JavaTip> tips = tipsDTO.stream()
                .map(dto -> {
                    JavaTip tip = new JavaTip();
                    tip.setTip(dto.getTip());
                    return tip;
                })
                .collect(Collectors.toList());

        List<JavaTip> savedTips = javaTipRepository.saveAll(tips);
        List<JavaTipDTO> savedTipsDTO = savedTips.stream()
                .map(tip -> new JavaTipDTO(tip.getId(), tip.getTip()))
                .collect(Collectors.toList());

        log.info("Successfully added tips: {}", savedTipsDTO);
        return savedTipsDTO;
    }

    // Method to fetch all tips
    @Transactional(readOnly = true)
    public List<JavaTipDTO> getAllTips() {
        log.info("Fetching all tips");
        List<JavaTip> tips = javaTipRepository.findAll();
        if (tips.isEmpty()) {
            log.warn("No tips found");
            throw new TipNotFoundException("No tips found");
        }
        List<JavaTipDTO> tipsDTO = tips.stream()
                .map(tip -> new JavaTipDTO(tip.getId(), tip.getTip()))
                .collect(Collectors.toList());

        log.info("Successfully retrieved all tips: {}", tipsDTO);
        return tipsDTO;
    }
}
