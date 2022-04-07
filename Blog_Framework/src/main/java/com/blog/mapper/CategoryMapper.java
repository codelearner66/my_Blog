package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-22 22:12:03
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}

