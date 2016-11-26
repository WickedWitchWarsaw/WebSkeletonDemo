
package com.wickedwitch.backend.services;

import com.wickedwitch.web.domain.frontend.FeedbackPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import javax.validation.Valid;

/**
 * Created by ZuZ on 2016-11-26.
 */
public abstract class AbstractEmailService implements EmailService {

    @Value("${default.to.address}")
    private String defaultToAddress;

    protected SimpleMailMessage prapareSimpleMailMessage(FeedbackPojo feedbackPojo){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(defaultToAddress);
        message.setFrom(feedbackPojo.getEmail());
        message.setSubject("WickedDemoWebSkeleton: sent test feedback "
                + feedbackPojo.getFirstName()
                + feedbackPojo.getLastName());
        message.setText(feedbackPojo.getFeedback());
        return message;
    }

    @Override
    public void sendFeedbackEmail(FeedbackPojo feedbackPojo) {
        sendGenericEmailMessage(prapareSimpleMailMessage(feedbackPojo));
    }
}
