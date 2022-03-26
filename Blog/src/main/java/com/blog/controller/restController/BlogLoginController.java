package com.blog.controller.restController;


import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.pojo.User;
import com.blog.service.BlogLoginService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@Validated User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @GetMapping("/logout")
    public ResponseResult logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token_cookie".equals(cookie.getName())) {
                // 删除 cookie
                cookie.setMaxAge(0);
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
