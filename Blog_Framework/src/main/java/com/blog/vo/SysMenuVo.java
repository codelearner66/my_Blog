package com.blog.vo;

import com.blog.pojo.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuVo {
    private List<SysMenu> list;
    //当前页码
    private Long current;

    private boolean isPre;

    private boolean isNext;

    private Long total;
}
