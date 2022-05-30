package com.blog.service.impl;


import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.pojo.LoginUser;
import com.blog.pojo.User;
import com.blog.service.BlogLoginService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.JwtUtil;
import com.blog.utils.MailUtils;
import com.blog.utils.RedisCache;
import com.blog.vo.BlogUserLoginVo;
import com.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    MailUtils mailutils;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    //登录
    @Override
    public ResponseResult login(User user) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate;
        //不捕获可能的认证异常 抛出后统一处理
        authenticate = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //从redis中获取用户信息
        LoginUser oldLoginUser = redisCache.getCacheObject("bloglogin:" + userId);
        if (!oldLoginUser.isAccountNonLocked()){
            // 被锁定或则封禁 提示重新登录
            return ResponseResult.errorResult(AppHttpCodeEnum.ACCOUNT_LOCKED);
        }
        //把用户信息存入redis
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);
        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
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
        //从redis中获取用户信息
        LoginUser oldLoginUser = redisCache.getCacheObject("bloglogin:" + userId);
        if (!oldLoginUser.isAccountNonLocked()){
            // 被锁定或则封禁 提示重新登录
            return ResponseResult.errorResult(AppHttpCodeEnum.ACCOUNT_LOCKED);
        }
        //删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }

    //注册
    @Override
    public ResponseResult register(User user) {
        //todo 注册时给默认权限 即给一个role角色
        return userService.register(user);
    }

    /**
     * 找回密码
     *
     * @param email
     * @return
     */
    @Override
    //@Transactional
    public ResponseResult findpass(String email) {
        User user = userService.getUserByEmails(email);
        //通过email找到用户信息
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        //修改新密码
        Random random = new Random();
        int newpass = random.nextInt(10000);
        user.setPassword(String.valueOf(newpass));
        //事务 邮件发送和修改密码同时进行 将邮件发送独立成工具类
        userService.updatePassword(user);
        mailutils.sendSimply(
                "my_Blog 找回密码！！！",
                "尊敬的用户： " + user.getNickName() + "您的密码是：  " + newpass,
                      email);
        //将新密码发送到用户email帐号
        return ResponseResult.okResult();
    }
}
