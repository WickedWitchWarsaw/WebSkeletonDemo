package com.wickedwitch.config;

import com.wickedwitch.backend.services.EmailService;
import com.wickedwitch.backend.services.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ZuZ on 2016-11-18.
 */
@Configuration
@Profile("dv")
@PropertySource("file:///${user.home}/dev/application-dv.properties")
public class DevelopmentConfig {

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
