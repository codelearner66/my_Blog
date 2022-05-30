package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.SysRoleMapper;
import com.blog.pojo.SysRole;
import com.blog.service.SysRoleService;
import com.blog.vo.SysRoleVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public ResponseResult getRoleLest() {
        return ResponseResult.okResult(list());
    }

    @Override
    public ResponseResult getRoleByIds(Long... ids) {
        List<SysRole> list = new ArrayList<>();
        for (Long id : ids) {
           list.add(getById(id)) ;
        }
        return ResponseResult.okResult(list);
    }

    @Override
    //分页查询
    public ResponseResult getRoleLest(Long page, int size) {
        Page<SysRole> rolePage = new Page<>(page, size);
        page(rolePage);
        SysRoleVo sysRoleVo = new SysRoleVo();
        sysRoleVo.setList(rolePage.getRecords());
        sysRoleVo.setNext(rolePage.hasNext());
        sysRoleVo.setPre(rolePage.hasPrevious());
        long pageTotal= rolePage.getTotal()%size==0?rolePage.getTotal()/size:rolePage.getTotal()/size+1;
        sysRoleVo.setTotal(Arrays.asList(new Long[Math.toIntExact(pageTotal)]));
        sysRoleVo.setCurrent(rolePage.getCurrent());
        return ResponseResult.okResult(sysRoleVo);
    }

    @Override
    public ResponseResult updateRole(SysRole sysRole) {
        if(!Objects.isNull(sysRole)){
            //更新
            Long id = sysRole.getId();
            if(id !=null){
                return updateById(sysRole) ?ResponseResult.okResult():ResponseResult.errorResult(550,"角色更新出错！");
            }
           // 新建
            return save(sysRole)?ResponseResult.okResult(): ResponseResult.errorResult(551,"新建角色出错！");
        }

        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult deleteRole(Long id) {

        return removeById(id)? ResponseResult.okResult():ResponseResult.errorResult(551,"删除角色出错！");
    }
}
