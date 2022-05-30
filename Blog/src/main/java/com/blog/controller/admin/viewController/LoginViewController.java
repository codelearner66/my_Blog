package com.blog.controller.admin.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LoginViewController {
    /**
     * 重新登陆
     * @return
     */
    @RequestMapping("/relogin")
    public String reLogin(){
        return "admin/pages_login";
    }


}
