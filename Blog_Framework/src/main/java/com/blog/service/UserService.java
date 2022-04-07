package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-02-09 00:28:29
 */
public interface UserService extends IService<User> {
    ResponseResult userInfo();
    User getUserByEmails(String mail);
    ResponseResult register(User user);

    ResponseResult updateUser(User user);

    ResponseResult updatePassword(User user);
    Boolean updateUserHeader(User user);
}

