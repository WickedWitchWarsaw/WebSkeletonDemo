package com.wickedwitch.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ZuZ on 2017-01-08.
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.wickedwitch.backend.persistance.repositories")
@EntityScan(basePackages = "com.wickedwitch.backend.persistance.domain.backend")
@EnableTransactionManagement
public class ApplicationConfig {
}
