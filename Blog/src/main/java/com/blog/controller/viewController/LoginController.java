package com.blog.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    @RequestMapping("/tologin")
    public String tologin() {
        return "login";
    }
}
