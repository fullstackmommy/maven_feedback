package com.mavenproject.feedback.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavenproject.feedback.model.Feedback;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataMongoTest
public class FeedbackRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FeedbackRepository feedbackRepository;

    private static File DATA_JSON = Paths.get("src", "test", "resources", "data", "data.json").toFile();

    @BeforeEach
    public void beforeEach() throws IOException {
        // Deserialize feedback from JSON file to Feedback array
        Feedback[] feedback = new ObjectMapper().readValue(DATA_JSON, Feedback[].class);

        // Save each feedback to MongoDB
        Arrays.stream(feedback).forEach(mongoTemplate::save);
    }

    @AfterEach
    public void afterEach() {
        mongoTemplate.dropCollection("Feedback");
    }

    @Test
    @DisplayName("Find feedback by id")
    public void testFindFeedbackById() {
        Optional<Feedback> feedback = feedbackRepository.findById("1");

        Assertions.assertTrue(feedback.isPresent(), "A feedback with id 1 should exist");
        feedback.ifPresent(f -> {
            Assertions.assertEquals("1", f.getId());
            Assertions.assertEquals(1, f.getProductId().intValue());
            Assertions.assertEquals(1, f.getUserId().intValue());
            Assertions.assertEquals("POSTED", f.getStatus());
        });
    }

    @Test
    @DisplayName("Find all feedback")
    public void testFindAllFeedback(){
        List<Feedback> feedbackList = feedbackRepository.findAll();

        Assertions.assertEquals(3, feedbackList.size());
    }
}
