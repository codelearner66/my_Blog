package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Comment;

public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult ChildCommentList(Long articleId, Long rootId, Integer pageNum, Integer pageSize);


    /**
     * 返回评论和子评论
     *
     * @param articleId   文章id
     * @param rootId      根评论id -1 为一级根
     * @param pageNum     根评论分页
     * @param pageSize    分页大小
     * @param commentNum  子评论论分页
     * @param commentSize 子评论分页大小
     * @return
     */
    ResponseResult commentList(Long articleId, Long rootId, Integer pageNum, Integer pageSize, Long commentNum, Long commentSize);

    ResponseResult addComments(Comment comment);
}