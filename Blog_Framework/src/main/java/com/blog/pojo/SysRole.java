package com.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 角色表(SysRole)表实体类
 *
 * @author makejava
 * @since 2022-05-07 19:54:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
public class SysRole  {
    @TableId
    private Long id;

    
    private String name;
    //角色权限字符串
    private String roleKey;
    //角色状态（0正常 1停用）
    private String status;
    //del_flag
    private Integer delFlag;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //备注
    private String remark;



}

