package com.blog.controller.viewController;

import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleViewController {
    @Autowired
    private ArticleService articleService;
    /**
     * 进行文章分类跳转
     * @param id 文章分类id
     * @param model
     * @return
     */
    @GetMapping("/articleView/{id}")
    public String  getArticleDetail(@PathVariable("id") Long id , Model model){
        System.out.println("articleView-------------------->"+id);
        model.addAttribute("articleId",id);
        model.addAttribute("articleDetail",articleService.getArticleDetail(id).getData());
        return "article";
    }
}
