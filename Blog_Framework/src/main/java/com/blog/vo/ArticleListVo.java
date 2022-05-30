package com.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private Long  categoryId;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;

    private Date createTime;


}