package com.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.UserMapper;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-02-09 00:28:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
    @Override
    public ResponseResult register(User user) {
//ToDO 检验email是否唯一
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setCreateTime(new Date());
        user.setNickName("小老弟");
        user.setUpdateTime(new Date());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        long count = count(queryWrapper);
        queryWrapper.eq(User::getEmail,user.getEmail());
        long count1 = count(queryWrapper);
        if (count>0||count1 > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        boolean save = save(user);
        if (save) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public User getUserByEmails(String mail) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,mail);
        UserMapper baseMapper = getBaseMapper();
        User user = baseMapper.selectOne(queryWrapper);
        return user;
    }
    @Override
    /**
     * 更新用户信息
     * 通过条件判断与之前保存的不同才会进行更新
     */
    public ResponseResult updateUser(User user) {
        if (user == null) {
            throw new RuntimeException("用户为空");
        }
        User oldUser= SecurityUtils.getLoginUser().getUser();
        //todo 判断修改过的字段 进行更新
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",oldUser.getId());
        updateWrapper.set(!oldUser.getNickName().equals(user.getNickName()),"nick_name",user.getNickName())
                .set(!oldUser.getEmail().equals(user.getEmail()),"email",user.getEmail())
                .set(!oldUser.getSex().equals(user.getSex()),"sex",user.getSex());
        if (!"".equals(user.getPassword())){
            String encode = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            updateWrapper.set(!oldUser.getPassword().equals(user.getPassword()),"password",user.getPassword());
        }
        boolean update = update(null, updateWrapper);
        if (update) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @Override
    public ResponseResult updatePassword(User user) {
        if (user == null) {
            throw new RuntimeException("用户为空");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",user.getId());
        if (!"".equals(user.getPassword())){
            String encode = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            updateWrapper.set("password",user.getPassword());
        }
        boolean update = update(null, updateWrapper);
        if (update) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    /**
     * 更新头像
     */
    public Boolean updateUserHeader(User user) {
        LambdaUpdateWrapper<User> lambdaUpdate= Wrappers.<User> lambdaUpdate()
                .eq(User::getId,user.getId());
        return update(user, lambdaUpdate);
    }


}

