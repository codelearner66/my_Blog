package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.SysRole;


/**
 * 角色表(SysRole)表服务接口
 *
 * @author ccx
 * @since 2022-05-07 19:54:02
 */
public interface SysRoleService extends IService<SysRole> {

     ResponseResult getRoleLest();
    ResponseResult getRoleByIds(Long...ids);
    ResponseResult getRoleLest(Long page,int size);

    ResponseResult updateRole(SysRole sysRole);

    ResponseResult deleteRole(Long id);

}

