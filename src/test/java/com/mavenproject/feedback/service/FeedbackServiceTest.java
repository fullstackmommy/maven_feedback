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
}
