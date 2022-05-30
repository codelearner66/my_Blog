package com.blog.controller.user.viewController;

import com.blog.annotation.SystemLog;
import com.blog.commonResult.ResponseResult;
import com.blog.service.ArticleService;
import com.blog.service.CommentService;
import com.blog.utils.BeanCopyUtils;
import com.blog.vo.ArticleListVo;
import com.blog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class ArticleViewController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    /**
     * 进行文章跳转
     * @param id 文章id
     * @param model
     * @return
     */
    @GetMapping("/articleView/{id}")
    public String  getArticleDetail(@PathVariable("id") Long id , Model model){
        model.addAttribute("articleId",id);
        model.addAttribute("articleDetail",articleService.getArticleDetail(id).getData());
        return "user/article";
    }
    @SystemLog(BusinessName = "文章详情")
    @GetMapping("/articleView")
    public String  getArticleDetail(Long id ,Long rootId, Integer pageNum, Integer pageSize, Long commentNum, Long commentSize ,Model model){
        model.addAttribute("articleId",id);
        PageVo data = (PageVo)commentService.commentList(id,rootId,pageNum,pageSize,commentNum,commentSize).getData();
        model.addAttribute("rootComments",data.getRows());
        model.addAttribute("commentsTotal",data.getTotal());
        model.addAttribute("articleDetail",articleService.getArticleDetail(id).getData());
        return "user/article";
    }

    @GetMapping("/articleList")
    public String getArticleList( Integer pageNum, Integer pageSize, Long categoryId,Model model){
        if (Objects.isNull(pageNum)) {
            pageNum=1;
        }
        if (categoryId == null) {
            categoryId = 1L;
        }
        if(pageSize==null){
            pageSize = 10;
        }
        ResponseResult responseResult = articleService.articleList(pageNum, pageSize, categoryId);
        if (responseResult.getCode()==200){
            PageVo data = (PageVo) responseResult.getData();
            List<ArticleListVo> list = BeanCopyUtils.copyBeanList(data.getRows(), ArticleListVo.class);
            model.addAttribute("type",categoryId);
            model.addAttribute("total",((PageVo) responseResult.getData()).getTotal());
            model.addAttribute("articleList",list);
        }
        return "user/articleList";
    }

    @GetMapping("/toAddArticle")
    public String toAddArticle(Integer pageNum, Integer pageSize, Long categoryId,Model model){
        if (Objects.isNull(pageNum)) {
            pageNum=1;
        }
        if (categoryId == null) {
            categoryId = 1L;
        }
        if(pageSize==null){
            pageSize = 10;
        }
        ResponseResult responseResult = articleService.articleListByUserId(pageNum, pageSize, categoryId);
        if (responseResult.getCode()==200){
            PageVo data = (PageVo) responseResult.getData();
            List<ArticleListVo> list = BeanCopyUtils.copyBeanList(data.getRows(), ArticleListVo.class);
            model.addAttribute("type",categoryId);
            model.addAttribute("total",((PageVo) responseResult.getData()).getTotal());
            model.addAttribute("articleList",list);
        }
        return "user/addArticle";
    }
  @GetMapping("/totoAddArticleDim")
    public String totoAddArticleDim(String str,Model model){
      ResponseResult articlesDim = articleService.getArticlesDim(str);
      if (articlesDim.getCode()==200){
          PageVo data = (PageVo) articlesDim.getData();
          List<ArticleListVo> list = BeanCopyUtils.copyBeanList(data.getRows(), ArticleListVo.class);
          model.addAttribute("total",((PageVo) articlesDim.getData()).getTotal());
          model.addAttribute("articleList",list);
      }
      return "user/addArticle";
  }
}
