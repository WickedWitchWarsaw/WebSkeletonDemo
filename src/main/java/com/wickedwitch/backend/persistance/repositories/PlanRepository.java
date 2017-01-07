package com.wickedwitch.backend.persistance.repositories;

import com.wickedwitch.backend.persistance.domain.backend.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ZuZ on 2017-01-07.
 */
@Repository
public interface PlanRepository extends CrudRepository<Plan, Integer> {
}
