package com.wickedwitch.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ZuZ on 2017-01-06.
 */
@Controller
public class PayloadController {

    @RequestMapping("/payload")
    public String payload(){
        return "payload/payload";
    }


}
