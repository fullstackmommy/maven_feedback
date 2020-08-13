package com.mavenproject.feedback.controller;

import com.mavenproject.feedback.model.Feedback;
import com.mavenproject.feedback.service.FeedbackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class FeedbackController {

    private static final Logger LOGGER = LogManager.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedback")
    public Iterable<Feedback> getAllFeedbackForProduct(@RequestParam(value = "productId", required = false) Optional<String> productId){
        return productId.map(id -> feedbackService.findByProductId(Integer.valueOf(id))
                .map(Arrays::asList)
                .orElseGet(ArrayList::new))
                .orElse(feedbackService.findAll());
    }
}
