package com.mavenproject.feedback.repository;

import com.mavenproject.feedback.model.Feedback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@DataMongoTest
@ExtendWith(MongoSpringExtension.class)
public class FeedbackRepositoryBetterTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Will only be used by the annotation
    public MongoTemplate getMongoTemplate(){
        return mongoTemplate;
    }

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    @DisplayName("Find all feedback with better_data.json dataset")
    @MongoTestDataFile(value = "better_data.json", classType = Feedback.class, collectionName = "Feedback")
    public void testGetFeedbackWithNewDataSet() {
        List<Feedback> feedbackList = feedbackRepository.findAll();

        Assertions.assertEquals(6, feedbackList.size());
    }

    @Test
    @DisplayName("Find all feedback with data.json dataset")
    @MongoTestDataFile(value = "data.json", classType = Feedback.class, collectionName = "Feedback")
    public void testGetFeedbackWithPreviousDataSet() {
        List<Feedback> feedbackList = feedbackRepository.findAll();

        Assertions.assertEquals(3, feedbackList.size());
    }

    @Test
    @DisplayName("Save a new feedback with better_data.json dataset")
    @MongoTestDataFile(value = "better_data.json", classType = Feedback.class, collectionName = "Feedback")
    public void testSavingNewFeedback(){
        Feedback newFeedback = new Feedback("8", 2, 1, "POSTED", "This product is great!");

        Feedback savedFeedback = feedbackRepository.save(newFeedback);

        Assertions.assertEquals(1, savedFeedback.getVersion().intValue());
        Assertions.assertEquals(7, feedbackRepository.count());
    }
}
