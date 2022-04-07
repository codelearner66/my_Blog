package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-03-22 22:12:06
 */
public interface CategoryService extends IService<Category> {
    ResponseResult getCategoryList();
}

