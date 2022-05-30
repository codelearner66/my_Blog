package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.mapper.SysRoleMenuMapper;
import com.blog.pojo.SysMenu;
import com.blog.pojo.SysRoleMenu;
import com.blog.service.SysMenuService;
import com.blog.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (SysRoleMenu)表服务实现类
 *
 * @author ccx
 * @since 2022-05-24 22:21:37
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
     @Autowired
    SysRoleMenuMapper mapper;
     @Autowired
    SysMenuService syService;

    @Override
    public List<SysMenu> getRoleMenuByRoleId(Long id) {
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,id);
        List<SysRoleMenu> sysRoleMenus = mapper.selectList(queryWrapper);
        return sysRoleMenus.stream()
                .distinct()
                .map(sysRoleMenu -> syService.getById(sysRoleMenu.getMenuId()))
                .collect(Collectors.toList());
    }
}

