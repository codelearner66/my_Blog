package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.pojo.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * (SysRoleMenu)表数据库访问层
 *
 * @author ccx
 * @since 2022-05-24 22:21:34
 */
@Mapper
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

}

