package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.pojo.SysMenu;
import com.blog.pojo.SysRoleMenu;

import java.util.List;


/**
 * (SysRoleMenu)表服务接口
 *
 * @author ccx
 * @since 2022-05-24 22:21:37
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
     List<SysMenu> getRoleMenuByRoleId(Long id);
}

