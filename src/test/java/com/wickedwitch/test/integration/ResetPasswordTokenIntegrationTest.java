package com.wickedwitch.test.integration;

import com.wickedwitch.WebSkeletonDemoApplication;
import com.wickedwitch.backend.persistance.domain.backend.ResetPasswordToken;
import com.wickedwitch.backend.persistance.domain.backend.User;
import com.wickedwitch.backend.persistance.repositories.ResetPasswordTokenRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by ZuZ on 2017-02-05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebSkeletonDemoApplication.class)
public class ResetPasswordTokenIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void init(){
        Assert.assertFalse(expirationTimeInMinutes == 0);
    }

    @Test
    public void testTokenExpirationLength() throws Exception{
        User user = createUser(testName);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        String token = UUID.randomUUID().toString();

        LocalDateTime expectedTime = now.plusMinutes(expirationTimeInMinutes);
        ResetPasswordToken resetPasswordToken = createResetPasswordToken(token, user, now);

        LocalDateTime actualTime = resetPasswordToken.getExpiryDate();
        Assert.assertNotNull(actualTime);
        Assert.assertEquals(expectedTime, actualTime);
    }

    @Test
    public void testFindTokenByTokenValue() throws Exception{
        User user = createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        createResetPasswordToken(token, user, now);

        ResetPasswordToken retrievePasswordResetToken = resetPasswordTokenRepository.findByToken(token);
        Assert.assertNotNull(retrievePasswordResetToken);
        Assert.assertNotNull(retrievePasswordResetToken.getId());
        Assert.assertNotNull(retrievePasswordResetToken.getUser());
    }

    @Test
    public void testDeleteToken() throws Exception{
        User user = createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        ResetPasswordToken resetPasswordToken = createResetPasswordToken(token, user, now);
        long tokenId = resetPasswordToken.getId();
        resetPasswordTokenRepository.delete(tokenId);

        ResetPasswordToken shouldNotExistToken = resetPasswordTokenRepository.findOne(tokenId);
        Assert.assertNull(shouldNotExistToken);
    }

    @Test
    public void testCascadeDeleteFromUserEntity() throws Exception{
        User user = createUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        ResetPasswordToken resetPasswordToken = createResetPasswordToken(token, user, now);
        resetPasswordToken.getId();

        userRepository.delete(user.getId());

        Set<ResetPasswordToken> shouldBeEmpty = resetPasswordTokenRepository.findAllByUserId(user.getId());
        Assert.assertTrue(shouldBeEmpty.isEmpty());
    }

    @Test
    public void testMultipleTokensAreReturnedWhenQueringByUser() throws Exception{
        User user = createUser(testName);
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        String token1 = UUID.randomUUID().toString();
        String token2 = UUID.randomUUID().toString();
        String token3 = UUID.randomUUID().toString();

        Set<ResetPasswordToken> tokens = new HashSet<>();
        tokens.add(createResetPasswordToken(token1, user, now));
        tokens.add(createResetPasswordToken(token2, user, now));
        tokens.add(createResetPasswordToken(token3, user, now));

        resetPasswordTokenRepository.save(tokens);

        User foundUser = userRepository.findOne(user.getId());

        Set<ResetPasswordToken> actualTokens = resetPasswordTokenRepository.findAllByUserId(user.getId());
        Assert.assertTrue(actualTokens.size() == tokens.size());
        List<String> tokensAsList = tokens.stream().map(rpt -> rpt.getToken()).collect(Collectors.toList());
        List<String> actualsTokensAsList = actualTokens.stream().map(rpt -> rpt.getToken()).collect(Collectors.toList());
        Assert.assertEquals(tokensAsList, actualsTokensAsList);
    }


    private ResetPasswordToken createResetPasswordToken(String token, User user, LocalDateTime now) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, user, now, expirationTimeInMinutes);
        resetPasswordTokenRepository.save(resetPasswordToken);
        Assert.assertNotNull(resetPasswordToken.getId());
        return resetPasswordToken;
    }

}
