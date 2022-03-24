package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Comment;

public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}