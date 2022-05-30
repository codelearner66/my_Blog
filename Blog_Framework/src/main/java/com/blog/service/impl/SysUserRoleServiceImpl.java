package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.commonResult.ResponseResult;
import com.blog.mapper.SysUserRoleMapper;
import com.blog.pojo.SysRole;
import com.blog.pojo.SysUserRole;
import com.blog.service.SysRoleService;
import com.blog.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (SysUserRole)表服务实现类
 *
 * @author ccx
 * @since 2022-05-18 21:54:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
     @Autowired
     SysUserRoleMapper sysUserRoleMapper;
     @Autowired
    SysRoleService sysRoleService;
    @Override
    public List<SysRole> getSysRoleByUserId(Long id) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new  LambdaQueryWrapper<>();

        queryWrapper.eq(SysUserRole::getUserId,id);

        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(queryWrapper);

        List<SysRole> collect = sysUserRoles.stream()
                .distinct()
                .map(sysUserRole -> sysRoleService.getById(sysUserRole.getRoleId()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public ResponseResult addUserRoleByUserId(Long[] userIds, Long[] roleIds) {
        boolean flag=false;
        for (Long userId : userIds) {
            for (Long roleId : roleIds) {
                SysUserRole sysUserRole=new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                flag= save(sysUserRole);
            }

        }
        return flag?ResponseResult.okResult():ResponseResult.errorResult(670,"用户添加角色出错！");
    }

}

