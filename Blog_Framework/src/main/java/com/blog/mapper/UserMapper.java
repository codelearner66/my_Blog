package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.pojo.User;
import com.blog.vo.UserCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-03 16:25:39
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<UserCount> getUsersCount();
    List<UserCount>   getSamthingCount(String tableName);
}

