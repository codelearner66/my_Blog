package com.blog.controller.user.restController;

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

    //获取热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {

        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    //    分类文章列表
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }

    //文章详情
    //@PreAuthorize("user_article")
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    //更新阅读量
    @GetMapping("/addViewCount/{id}")
    public ResponseResult addViewCount(@PathVariable("id") Long id) {
        return articleService.addViewCount(id);
    }

    //添加文章
    @PostMapping("/addArticle")
    public ResponseResult addArticle(Article article,String stat) {
        return articleService.addArticle(article,stat);
    }

    //文章模糊查询
    @GetMapping("/getArticleDim")
    public ResponseResult getArticleDim(String str){
        return articleService.getArticlesDim(str);
    }

}
