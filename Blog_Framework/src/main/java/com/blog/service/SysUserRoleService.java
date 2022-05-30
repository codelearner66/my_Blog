package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.SysRole;
import com.blog.pojo.SysUserRole;

import java.util.List;


/**
 * (SysUserRole)表服务接口
 *
 * @author ccx
 * @since 2022-05-18 21:54:47
 */
public interface SysUserRoleService extends IService<SysUserRole> {

     List<SysRole> getSysRoleByUserId(Long id);

     public ResponseResult addUserRoleByUserId(Long []userIds,Long [] roleIds);
}

