package com.blog.vo;

import com.blog.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class AdminVo {
    private List<User>  list;
    //当前页码
    private Long current;
   //是否有前页
    private boolean isPre;
    //是否有后页
    private boolean isNext;
   //总页数
    private Long total;
}
