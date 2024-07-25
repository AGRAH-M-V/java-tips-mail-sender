package com.example.javatips.repository;

import com.example.javatips.model.JavaTip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JavaTipRepository extends MongoRepository<JavaTip, String> {
}
