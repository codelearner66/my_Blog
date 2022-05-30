package com.blog.service;

import com.blog.pojo.SysMenu;

import java.util.List;

/**
 * 授权管理
 */
public interface AuthorizationService {
    /**
     *  添加一个权限
     * @param id 需要添加权限的用户id
     * @param sysMenu 权限
     * @return  是否添加成功
     */
     boolean addAuthorization(Long id, SysMenu sysMenu);
     boolean addAuthorization(Long id, List<SysMenu> sysMenu);

    /**
     *  删除一个权限
     * @param id  用户id
     * @param sysMenu 权限
     * @return 是否成功
     */
    boolean deleteAuthorization(Long id, SysMenu sysMenu);

    /**
     * 锁定用户
     * @param id 用户id
     * @return 是否成功
     */
    boolean lockUser(Long id);

    boolean unlockUser(Long id);
}
