package com.wickedwitch.backend.persistance.repositories;

import com.wickedwitch.backend.persistance.domain.backend.ResetPasswordToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by ZuZ on 2017-02-05.
 */
@Repository
public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, Long>{

    ResetPasswordToken findByToken(String token);

    @Query("SELECT rpt FROM ResetPasswordToken rpt INNER JOIN rpt.user u WHERE rpt.user.id = ?")
    Set<ResetPasswordToken> findAllByUserId(long userId);
}
