package com.wickedwitch.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


/**
 * Created by ZuZ on 2016-11-26.
 *
 * Real implementation of email service
 */

public class SmptEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(SmptEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage message) {
        LOG.debug("Sending email for: ()", message);
        mailSender.send(message);
        LOG.info("Email SENT!!");

    }
}
