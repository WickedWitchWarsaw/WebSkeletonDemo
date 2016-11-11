package com.wickedwitch.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ZuZ on 2016-11-11.
 */

@Controller
public class IndexController {
    @RequestMapping("/")
    public String home(){
        return "index";
    }
}
