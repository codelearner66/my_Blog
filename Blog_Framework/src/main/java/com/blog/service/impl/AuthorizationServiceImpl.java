package com.blog.service.impl;

import com.blog.pojo.LoginUser;
import com.blog.pojo.SysMenu;
import com.blog.pojo.User;
import com.blog.service.AuthorizationService;
import com.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    RedisCache redisCache;
    private  final String pre="bloglogin:";
    @Override
    public boolean addAuthorization(Long id, SysMenu sysMenu) {
        //从redis中获取用户信息
        LoginUser loginUser =  redisCache.getCacheObject(pre + id);
        if (loginUser == null) {
            throw new RuntimeException("用户不存在！");
        }
        //获取之前的权限列表 并添加新的权限
        List<String> list = loginUser.getList();
        list.add(sysMenu.getPerms());
        //删除旧的缓存信息
        redisCache.deleteObject(pre+id);
        // 构建新的用户信息
        LoginUser newLoginUser = new LoginUser(loginUser.getUser(),list);

        //设置30分钟时长
        redisCache.setCacheObject(pre+id,newLoginUser,30, TimeUnit.MINUTES);
        return false;
    }

    @Override
    public boolean addAuthorization(Long id, List<SysMenu> sysMenu) {
        //从redis中获取用户信息
        LoginUser loginUser = getLoginUser(id);
        //获取之前的权限列表 并添加新的权限
        List<String> list = loginUser.getList();
        for (SysMenu menu : sysMenu) {
            list.add(menu.getPerms());
        }
        List<String> collect = list.stream().distinct().collect(Collectors.toList());

        return save(loginUser,collect);
    }

    @Override
    public boolean deleteAuthorization(Long id, SysMenu sysMenu) {
        //从redis中获取用户信息
        LoginUser loginUser = getLoginUser(id);
        //获取之前的权限列表 并添加新的权限
        List<String> list = loginUser.getList();
        // 把指定的权限去除
        List<String> collect = list.stream()
                .filter(s -> sysMenu.getPerms().equals(s))
                .collect(Collectors.toList());
        return   save(loginUser,collect);
    }

    @Override
    public boolean lockUser(Long id) {
        //从redis中获取用户信息
        LoginUser loginUser = getLoginUser(id);
        User user = loginUser.getUser();
        user.setStatus("1");
        return save(loginUser,loginUser.getList());
    }

    @Override
    public boolean unlockUser(Long id) {
        //从redis中获取用户信息
        LoginUser loginUser = getLoginUser(id);
        User user = loginUser.getUser();
        user.setStatus("0");
        return  save(loginUser,loginUser.getList());
    }

    private LoginUser getLoginUser(Long id){
        //从redis中获取用户信息
        LoginUser loginUser =  redisCache.getCacheObject(pre + id);
        if (loginUser == null) {
            throw new RuntimeException("用户不存在！");
        }
        return loginUser;
    }

    private boolean save(LoginUser loginUser,List<String> list){
        //删除旧的缓存信息
        redisCache.deleteObject(pre+loginUser.getUser().getId());
        // 构建新的用户信息
        LoginUser newLoginUser = new LoginUser(loginUser.getUser(),list);
        //设置30分钟时长
        redisCache.setCacheObject(pre+loginUser.getUser().getId(),newLoginUser,30, TimeUnit.MINUTES);
        return true;
    }
}
