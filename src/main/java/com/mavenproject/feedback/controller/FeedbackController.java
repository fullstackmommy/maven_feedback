package com.mavenproject.feedback.controller;

import com.mavenproject.feedback.model.Feedback;
import com.mavenproject.feedback.service.FeedbackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> getFeedback(@PathVariable String id){
        LOGGER.info("Searching for feedback with id:{}", id);
        return feedbackService.findById(id)
                .map(feedback -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .location(new URI("/feedback/" + id))
                                .eTag(Integer.toString(feedback.getVersion()))
                                .body(feedback);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback){
        LOGGER.info("Adding a new feedback for product:{}", feedback.getProductId());

        feedback = feedbackService.save(feedback);

        try {
            return ResponseEntity
                    .created(new URI("/feedback/" + feedback.getId()))
                    .eTag(Integer.toString(feedback.getVersion()))
                    .body(feedback);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
