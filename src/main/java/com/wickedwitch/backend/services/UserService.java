package com.wickedwitch.backend.services;

import com.wickedwitch.backend.persistance.domain.backend.Plan;
import com.wickedwitch.backend.persistance.domain.backend.User;
import com.wickedwitch.backend.persistance.domain.backend.UserRole;
import com.wickedwitch.backend.persistance.repositories.PlanRepository;
import com.wickedwitch.backend.persistance.repositories.RoleRepository;
import com.wickedwitch.backend.persistance.repositories.UserRepository;
import com.wickedwitch.enums.PlansEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by ZuZ on 2017-01-08.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public User createUser(User user, PlansEnum planEnum, Set<UserRole> userRoles) {
        Plan plan = new Plan(planEnum);
        if (!planRepository.exists(planEnum.getId())) {
            plan = planRepository.save(plan);
        }

        user.setPlan(plan);

        for (UserRole ur : userRoles) {
            roleRepository.save(ur.getRole());
        }

        user.getUserRoles().addAll(userRoles);
        user = userRepository.save(user);

        return user;
    }
}
