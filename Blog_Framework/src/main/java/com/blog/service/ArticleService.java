package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult addViewCount(Long id);

    ResponseResult addArticle(Article article);
}
