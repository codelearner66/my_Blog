package com.blog.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (SysUserRole)表实体类
 *
 * @author ccx
 * @since 2022-05-18 21:54:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class SysUserRole  {
    //用户id@TableId
    private Long userId;
    //角色id@TableId
    private Long roleId;




}

