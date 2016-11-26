package com.wickedwitch.config;

import com.wickedwitch.backend.services.EmailService;
import com.wickedwitch.backend.services.SmptEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ZuZ on 2016-11-18.
 */
@Configuration
@Profile("prod")
@PropertySource("file:///${user.home}/dev/application-prod.properties")
public class ProductionConfig {

    @Bean
    public EmailService emailService(){
        return new SmptEmailService();
    }
}
