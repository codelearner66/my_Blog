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
   //获取用户总数
    ResponseResult getUserTotle();
    //获取每日增加用户数
    ResponseResult getUserCount();
    //获取管理员帐号
    ResponseResult getAdmins(String isAdmin,Long page,int size);
   //更新admin
    ResponseResult updateAdmins(User user);

    ResponseResult deleteAdmins(Long id);

    ResponseResult lackUser(Long id);

    ResponseResult unlackUser(Long id);

    ResponseResult adminSerach(String isAdmin,String type, String msg);
}

