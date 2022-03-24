package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-24 17:04:00
 */
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}

