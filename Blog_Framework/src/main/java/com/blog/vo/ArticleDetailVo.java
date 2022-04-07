package com.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
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
    //内容
    private  String content;
    //访问量
    private Long viewCount;

}
