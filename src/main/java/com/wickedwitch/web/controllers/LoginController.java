package com.wickedwitch.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ZuZ on 2017-01-06.
 */
@Controller
public class LoginController {

    public static final String LOGIN_VIEW_USER = "user/login";

    @RequestMapping("/login")
    public String login(){
        return LOGIN_VIEW_USER;
    }
}
