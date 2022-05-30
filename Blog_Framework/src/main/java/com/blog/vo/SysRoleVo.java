package com.blog.vo;

import com.blog.pojo.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleVo {

    private List<SysRole> list;
    //当前页码
    private Long current;

    private boolean isPre;

    private boolean isNext;

    private List<Long> total;
}
