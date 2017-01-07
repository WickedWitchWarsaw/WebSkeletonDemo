package com.wickedwitch.test.integration;

import com.wickedwitch.WebSkeletonDemoApplication;
import com.wickedwitch.backend.persistance.domain.backend.Plan;
import com.wickedwitch.backend.persistance.domain.backend.Role;
import com.wickedwitch.backend.persistance.domain.backend.User;
import com.wickedwitch.backend.persistance.domain.backend.UserRole;
import com.wickedwitch.backend.persistance.repositories.PlanRepository;
import com.wickedwitch.backend.persistance.repositories.RoleRepository;
import com.wickedwitch.backend.persistance.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZuZ on 2017-01-07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebSkeletonDemoApplication.class)
public class RepositoriesIntegrationTest {

    private static final Integer BASIC_PLAN_ID = 1;
    private static final Integer BASIC_ROLE_ID = 1;
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void init() {
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(roleRepository);
    }

    @Test
    public void testCreateNewPlan() throws Exception {
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);
        Plan retrievePlan = planRepository.findOne(BASIC_PLAN_ID);
        Assert.assertNotNull(retrievePlan);
    }

    @Test
    public void testCreateNewRole() throws Exception {
        Role basicRole = createBasicRole();
        roleRepository.save(basicRole);
        Role retreiveRole = roleRepository.findOne(BASIC_ROLE_ID);
        Assert.assertNotNull(retreiveRole);
    }

    @Test
    public void testCreateNewUser() throws Exception{
        Plan basicPlan = createBasicPlan();
        planRepository.save(basicPlan);

        User basicUser = createBasicUser();
        basicUser.setPlan(basicPlan);

        Role basicRole = createBasicRole();
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setUser(basicUser);
        userRole.setRole(basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);

        for(UserRole ur : userRoles){
            roleRepository.save(ur.getRole());
        }

        basicUser = userRepository.save(basicUser);
        User newlyCreatedUser = userRepository.findOne(basicUser.getId());
        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
        for(UserRole ur : newlyCreatedUserUserRoles){
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }
    }

    private Plan createBasicPlan() {
        Plan plan = new Plan();
        plan.setId(BASIC_PLAN_ID);
        plan.setName("Basic");
        return plan;
    }

    private Role createBasicRole() {
        Role role = new Role();
        role.setId(BASIC_ROLE_ID);
        role.setName("ROLE_USER");
        return role;
    }

    private User createBasicUser() {
        User user = new User();
        user.setUsername("basicUser");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("basicUser@user.com");
        user.setCountry("POL");
        user.setProfileImageUrl("https://www.blabla.image.com/basicuser.jpg");
        user.setEnabled(true);
        return user;
    }

}
