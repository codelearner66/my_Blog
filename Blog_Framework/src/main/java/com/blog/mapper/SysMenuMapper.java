package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.pojo.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 菜单表(SysMenu)表数据库访问层
 *
 * @author ccx
 * @since 2022-04-14 22:04:54
 */
@Mapper
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> getUserAuthorityList(Long id);
}

