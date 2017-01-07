package com.wickedwitch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.wickedwitch.backend.persistance.repositories")
public class WebSkeletonDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSkeletonDemoApplication.class, args);
	}
}
