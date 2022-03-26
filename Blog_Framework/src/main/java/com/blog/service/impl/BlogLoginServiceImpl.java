package com.blog.service.impl;


import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.pojo.LoginUser;
import com.blog.pojo.User;
import com.blog.service.BlogLoginService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.JwtUtil;
import com.blog.utils.RedisCache;
import com.blog.vo.BlogUserLoginVo;
import com.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    //登录
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }
   //登出
    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:"+userId);
        return ResponseResult.okResult();
    }
   //注册
    @Override
    public ResponseResult register(User user) {
        return userService.register(user);
    }

    /**
     * 找回密码
     * @param email
     * @return
     */
    @Override
    @Async
    public ResponseResult findpass(String email) {
        User user = userService.getUserByEmails(email);
        //通过email找到用户信息
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        //修改新密码
        Random random = new Random();
         int newpass= random.nextInt(1000000000);
         user.setPassword(String.valueOf(newpass));
         userService.updateUser(user);
        //将新密码发送到用户email帐号
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("my_Blog 找回密码！！！");
        String msg="尊敬的用户： "+user.getNickName()+
                "您的密码是：  "+newpass;
        message.setText(msg);
        message.setTo(email);
        message.setFrom("chenbyf1314@foxmail.com");
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
        return ResponseResult.okResult();
    }
}
