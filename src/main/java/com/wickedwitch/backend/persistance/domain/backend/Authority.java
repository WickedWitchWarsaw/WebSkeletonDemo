package com.wickedwitch.backend.persistance.domain.backend;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by ZuZ on 2017-01-29.
 */
public class Authority implements GrantedAuthority {

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
