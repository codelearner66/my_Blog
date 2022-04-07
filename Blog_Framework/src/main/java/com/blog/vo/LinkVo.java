package com.blog.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    @TableId
    private Long id;


    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;



}

