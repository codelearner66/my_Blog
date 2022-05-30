package com.blog.controller.user.viewController;

import com.blog.commonResult.ResponseResult;
import com.blog.service.ArticleService;
import com.blog.utils.BeanCopyUtils;
import com.blog.vo.ArticleListVo;
import com.blog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"/user","/"})
public class LoginController {
    @Autowired
    private ArticleService articleService;
    @RequestMapping({ "/","/user" })
    public String index(Model model) {

        ResponseResult responseResult = articleService.hotArticleListDetils();
        if (responseResult.getCode()==200){
            PageVo data = (PageVo) responseResult.getData();
            List<ArticleListVo> list = BeanCopyUtils.copyBeanList(data.getRows(), ArticleListVo.class);
            model.addAttribute("total",((PageVo) responseResult.getData()).getTotal());
            model.addAttribute("articleList",list);
        }
        return "user/index";
    }
    @RequestMapping("/tologin")
    public String tologin() {
        return "user/login";
    }
    @RequestMapping("/toWxlogin")
    public String wxlogin(){
        return "user/wxlogin";
    }
}
