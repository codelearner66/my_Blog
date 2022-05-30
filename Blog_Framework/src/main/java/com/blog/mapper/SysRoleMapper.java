package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author ccx
 * @since 2022-05-07 19:53:59
 */
@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

}

