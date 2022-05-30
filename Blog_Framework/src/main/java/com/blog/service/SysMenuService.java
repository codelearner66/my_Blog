package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.SysMenu;
import com.blog.pojo.User;

import java.util.List;


/**
 * 菜单表(SysMenu)表服务接口
 *
 * @author makejava
 * @since 2022-04-14 22:04:59
 */
public interface SysMenuService extends IService<SysMenu> {
    List<String> getUserAuthorityList(User user);

     ResponseResult getMenuLest();

    ResponseResult getMenuByIds(Long...ids);

    ResponseResult getMenuLest(Long pageId,int size);

     ResponseResult updateMenus(SysMenu sysMenu);

     ResponseResult deleteMenus(Long id);
}

