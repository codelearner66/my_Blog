package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.pojo.Link;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-22 22:52:37
 */
@Mapper
@Repository
public interface LinkMapper extends BaseMapper<Link> {

}

