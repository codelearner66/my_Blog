package com.blog.controller.user.restController;


import com.blog.commonResult.ResponseResult;
import com.blog.pojo.User;
import com.blog.service.BlogLoginService;
import com.blog.vo.BlogUserLoginVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@Valid User user, HttpServletResponse response){

        ResponseResult login = blogLoginService.login(user);
        if(login.getCode() == 200){
            BlogUserLoginVo vo= (BlogUserLoginVo) login.getData();
            Cookie cooki=new Cookie("token_cookie",vo.getToken());
            cooki.setMaxAge(1000*60*24*7);
            cooki.setPath("/");  //路径一定要写上，不然销毁不了
            response.addCookie(cooki);
        }
        return login;
    }
    @GetMapping("/logout")
    public ResponseResult logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token_cookie".equals(cookie.getName())) {
                // 删除 cookie
                cookie.setMaxAge(0);
                cookie.setPath("/");  //路径一定要写上，不然销毁不了
                response.addCookie(cookie);
            }
        }
        return blogLoginService.logout();
    }
    @PostMapping("/register")
    public ResponseResult register(@Validated User user) {
        return blogLoginService.register(user);
    }

    @PostMapping("/findpass")
    public ResponseResult findpass(@NotNull String email){
        return blogLoginService.findpass(email);
    }
}
