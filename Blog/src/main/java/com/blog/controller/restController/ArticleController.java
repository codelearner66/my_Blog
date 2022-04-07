package com.blog.controller.restController;

import com.blog.commonResult.ResponseResult;
import com.blog.pojo.Article;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {

        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @GetMapping("/addViewCount/{id}")
    public ResponseResult addViewCount(@PathVariable("id") Long id) {
        return articleService.addViewCount(id);
    }

    @PostMapping("/addArticle")
    public ResponseResult addArticle(Article article) {
        return articleService.addArticle(article);
    }

}
