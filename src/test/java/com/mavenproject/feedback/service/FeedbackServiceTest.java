package com.mavenproject.feedback.service;

import com.mavenproject.feedback.model.Feedback;
import com.mavenproject.feedback.repository.FeedbackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FeedbackServiceTest {

    @Autowired
    private FeedbackService feedbackService;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Test
    @DisplayName("Find feedback by id successfully")
    public void testFindFeedbackById(){
        Feedback mockFeedback = new Feedback("1", 1, 1, "POSTED", "This product is great!");

        doReturn(Optional.of(mockFeedback)).when(feedbackRepository).findById("1");

        Optional<Feedback> foundFeedback = feedbackService.findById("1");

        Assertions.assertTrue(foundFeedback.isPresent());
        Assertions.assertNotEquals(Optional.empty(), foundFeedback);
        foundFeedback.ifPresent(f -> {
            Assertions.assertEquals(1, f.getProductId().intValue());
            Assertions.assertEquals("POSTED", f.getStatus());
        });
    }

    @Test
    @DisplayName("Find feedback by product id successfully")
    public void testFindFeedbackByProductId(){
        Feedback mockFeedback = new Feedback("1", 1, 1, "POSTED", "This product is great!");

        doReturn(Optional.of(mockFeedback)).when(feedbackRepository).findByProductId(1);

        Optional<Feedback> foundFeedback = feedbackService.findByProductId(1);

        Assertions.assertTrue(foundFeedback.isPresent());
        Assertions.assertNotEquals(Optional.empty(), foundFeedback);
        foundFeedback.ifPresent(f -> {
            Assertions.assertEquals("1", f.getId());
            Assertions.assertEquals("POSTED", f.getStatus());
            Assertions.assertEquals(1, f.getUserId().intValue());
        });
    }

    @Test
    @DisplayName("Find feedback by id failure")
    public void testFindFeedbackByIdFailure(){
        doReturn(Optional.empty()).when(feedbackRepository).findByProductId(1);

        Optional<Feedback> foundFeedback = feedbackService.findByProductId(1);

        Assertions.assertFalse(foundFeedback.isPresent());
        Assertions.assertEquals(Optional.empty(), foundFeedback);
    }

    @Test
    @DisplayName("Find all feedback successfully")
    public void testFindAllFeedback(){
        Feedback mockFeedback1 = new Feedback("1", 1, 1, "POSTED", "This product is great!");
        Feedback mockFeedback2 = new Feedback("2", 1, 2, "POSTED", "This product is awesome!");

        doReturn(Arrays.asList(mockFeedback1, mockFeedback2)).when(feedbackRepository).findAll();

        List<Feedback> feedbackList = feedbackService.findAll();

        Assertions.assertNotNull(feedbackList);
        Assertions.assertEquals(2, feedbackList.size());
    }

    @Test
    @DisplayName("Save new feedback successfully")
    public void testSaveNewFeedback() {
        Feedback feedbackToSave = new Feedback("1", 1, 1, "POSTED", "This product is great!");
        Feedback feedbackToReturn = new Feedback("1", 1, 1, "POSTED", "This product is great!");

        doReturn(feedbackToReturn).when(feedbackRepository).save(feedbackToSave);

        Feedback savedFeedback = feedbackService.save(feedbackToSave);

        Assertions.assertNotNull(savedFeedback);
        Assertions.assertEquals(1, savedFeedback.getVersion().intValue());
    }

}
