package com.wickedwitch.backend.services;

import com.wickedwitch.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by ZuZ on 2016-11-26.
 */
public interface EmailService {

    /**
     * Sends an Email wit the contact in the Feedback Pojo.
     * @param feedbackPojo
     */
    void sendFeedbackEmail(FeedbackPojo feedbackPojo);

    void sendGenericEmailMessage(SimpleMailMessage message);
}
