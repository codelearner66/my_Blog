package com.blog.service;

import com.blog.commonResult.ResponseResult;
import com.blog.pojo.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult register(User user);

    ResponseResult findpass(String email);
}
