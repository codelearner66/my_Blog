package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Link;

public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}