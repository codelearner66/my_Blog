package com.blog.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (SysRoleMenu)表实体类
 *
 * @author ccx
 * @since 2022-05-24 22:21:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class SysRoleMenu  {
    //角色ID@TableId
    private Long roleId;
    //菜单id@TableId
    private Long menuId;




}

