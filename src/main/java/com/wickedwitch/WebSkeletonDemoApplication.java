package com.wickedwitch;

import com.wickedwitch.backend.persistance.domain.backend.Role;
import com.wickedwitch.backend.persistance.domain.backend.User;
import com.wickedwitch.backend.persistance.domain.backend.UserRole;
import com.wickedwitch.backend.services.UserService;
import com.wickedwitch.enums.PlansEnum;
import com.wickedwitch.enums.RolesEnum;
import com.wickedwitch.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class WebSkeletonDemoApplication implements CommandLineRunner {

	/** The application logger */
	private static final Logger LOG = LoggerFactory.getLogger(WebSkeletonDemoApplication.class);

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(WebSkeletonDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = UserUtils.createBasicUser();
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, new Role(RolesEnum.BASIC)));

		LOG.debug("Creating new user with userName {}" + user.getUsername());

		userService.createUser(user, PlansEnum.PRO, userRoles);

		LOG.info("User {} created", user.getUsername());

	}
}
