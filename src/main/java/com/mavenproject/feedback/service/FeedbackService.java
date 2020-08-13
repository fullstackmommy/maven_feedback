package com.mavenproject.feedback.service;

import com.mavenproject.feedback.model.Feedback;
import com.mavenproject.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Optional<Feedback> findById(String id){
        return feedbackRepository.findById(id);
    }

    public Optional<Feedback> findByProductId(Integer id){
        return feedbackRepository.findByProductId(id);
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public Feedback save(Feedback feedback) {
        feedback.setVersion(1);
        return feedbackRepository.save(feedback);
    }

    public void delete(String id) {
        feedbackRepository.deleteById(id);
    }
}
