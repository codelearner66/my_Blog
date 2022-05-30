package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
    ResponseResult hotArticleListDetils();
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
    ResponseResult articleListByUserId(Integer pageNum, Integer pageSize, Long categoryId);
    ResponseResult getArticleDetail(Long id);

    ResponseResult addViewCount(Long id);
   /*
   * 获取提交审核的文章
   * */
    ResponseResult getDraft();

    ResponseResult addArticle(Article article,String status);
    /**
     *  模糊查询
     * @param str
     * @return
     */
    ResponseResult getArticlesDim(String str);

    /**
     * 获取文章总数
     * @return
     */
    ResponseResult getArticleTotle();

    /**
     * 获取文章总浏览量
     * @return
     */
    ResponseResult getArticleBroTotle();

    /**
     * 获取新增文章总数
     * @return
     */
    ResponseResult getNewArticlesTotle();
    /**
     * 获取每日新增文章总数
     * @return
     */
    ResponseResult getNewDailyArticles();
/*
* 审核通过
* */
    ResponseResult passArticle(Long id);
/*
* 审核文章驳回
* */
    ResponseResult deleteArticle(Long id);
}
