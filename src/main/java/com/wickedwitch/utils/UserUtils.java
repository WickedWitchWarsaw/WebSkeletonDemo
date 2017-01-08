package com.wickedwitch.utils;

import com.wickedwitch.backend.persistance.domain.backend.User;

/**
 * Created by ZuZ on 2017-01-08.
 */
public class UserUtils {

    private UserUtils(){
        throw new AssertionError("Class not instantiable - Singleton");
    }

    public static User createBasicUser() {
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
