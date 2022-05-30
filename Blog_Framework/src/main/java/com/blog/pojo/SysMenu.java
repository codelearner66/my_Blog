package com.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 菜单表(SysMenu)表实体类
 *
 * @author ccx
 * @since 2022-04-14 22:04:57
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
public class SysMenu  {
    @TableId
    private Long id;

    //菜单名
    private String menuName;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //是否删除（0未删除 1已删除）
    private Integer delFlag;
    //备注
    private String remark;



}

