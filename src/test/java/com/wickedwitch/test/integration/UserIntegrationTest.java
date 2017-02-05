package com.wickedwitch.test.integration;

import com.wickedwitch.WebSkeletonDemoApplication;
import com.wickedwitch.backend.persistance.domain.backend.Plan;
import com.wickedwitch.backend.persistance.domain.backend.Role;
import com.wickedwitch.backend.persistance.domain.backend.User;
import com.wickedwitch.backend.persistance.domain.backend.UserRole;
import com.wickedwitch.backend.persistance.repositories.PlanRepository;
import com.wickedwitch.backend.persistance.repositories.RoleRepository;
import com.wickedwitch.backend.persistance.repositories.UserRepository;
import com.wickedwitch.enums.PlansEnum;
import com.wickedwitch.enums.RolesEnum;
import com.wickedwitch.utils.UserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static com.wickedwitch.utils.UserUtils.createBasicUser;

/**
 * Created by ZuZ on 2017-01-07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebSkeletonDemoApplication.class)
public class UserIntegrationTest extends AbstractIntegrationTest {

    @Rule
    public TestName testName = new TestName();


    @Before
    public void init() {
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(roleRepository);
    }

    @Test
    public void testCreateNewPlan() throws Exception {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievePlan = planRepository.findOne(PlansEnum.BASIC.getId());
        Assert.assertNotNull(retrievePlan);
    }

    @Test
    public void testCreateNewRole() throws Exception {
        Role userRole = createRole(RolesEnum.BASIC);
        roleRepository.save(userRole);
        Role retrieveRole = roleRepository.findOne(RolesEnum.BASIC.getId());
        Assert.assertNotNull(retrieveRole);
    }

    @Test
    public void createNewUser() throws Exception {
//        Plan basicPlan = createPlan(PlansEnum.BASIC);
//        planRepository.save(basicPlan);
//
//        User basicUser = UserUtils.createBasicUser();
//        basicUser.setPlan(basicPlan);
//
//        Role basicRole = createRole(RolesEnum.BASIC);
//        Set<UserRole> userRoles = new HashSet<>();
//        UserRole userRole = new UserRole(basicUser, basicRole);
//        userRoles.add(userRole);
//
//        basicUser.getUserRoles().addAll(userRoles);
//
//        for(UserRole ur : userRoles){
//            roleRepository.save(ur.getRole());
//        }

        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@gmail.com";

        User basicUser = createUser(username, email);

        basicUser = userRepository.save(basicUser);
        User newlyCreatedUser = userRepository.findOne(basicUser.getId());
        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Assert.assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
        for (UserRole ur : newlyCreatedUserUserRoles) {
            Assert.assertNotNull(ur.getRole());
            Assert.assertNotNull(ur.getRole().getId());
        }
    }

    public void testDeleteUser() throws Exception {

        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@gmail.com";

        User basicUser = createUser(username, email);
        userRepository.delete(basicUser.getId());
    }
}

