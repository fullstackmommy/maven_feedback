package com.mavenproject.feedback.controller;

import com.mavenproject.feedback.model.Feedback;
import com.mavenproject.feedback.service.FeedbackService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerTest {

    @MockBean
    private FeedbackService feedbackService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test all feedbacks found - GET /feedback")
    public void testAllFeedbackFound() throws Exception {
        Feedback firstFeedback = new Feedback("1", 1, 1, "POSTED", "This product is great!");
        Feedback secondFeedback = new Feedback("2", 1, 2, "PUBLISHED", "This product is awesome!");

        doReturn(Arrays.asList(firstFeedback, secondFeedback)).when(feedbackService).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/feedback"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[0].status", is("POSTED")))
                .andExpect(jsonPath("$[1].status", is("PUBLISHED")));
    }
}
