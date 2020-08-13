package com.mavenproject.feedback.repository;

import com.mavenproject.feedback.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {

    Optional<Feedback> findByProductId(Integer id);
}
