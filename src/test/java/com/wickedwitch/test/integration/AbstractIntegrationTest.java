package com.wickedwitch.test.integration;

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
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZuZ on 2017-02-05.
 */
public abstract class AbstractIntegrationTest {

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;


    protected Plan createPlan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }

    protected Role createRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    protected User createUser(String username, String email) {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser(username, email);
        basicUser.setPlan(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser = userRepository.save(basicUser);
        return basicUser;
    }

    protected User createUser(TestName testName){
        return createUser(testName.getMethodName(), testName.getMethodName() + "@gmail.com");
    }
}
