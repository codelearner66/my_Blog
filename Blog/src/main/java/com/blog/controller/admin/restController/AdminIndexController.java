package com.blog.controller.admin.restController;

import com.blog.annotation.SystemLog;
import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.pojo.LoginUser;
import com.blog.service.ArticleService;
import com.blog.service.UserService;
import com.blog.utils.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin_basic')")
public class AdminIndexController {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @SystemLog(BusinessName = "检验密码是否正确")
    @PostMapping("/checkpwd")
    public ResponseResult checkPwd(@NotNull String pwd) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if ( bCryptPasswordEncoder.matches(pwd,loginUser.getUser().getPassword())) {
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SUCCESS);
        }
        return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @SystemLog(BusinessName = "获取文章总数")
    @GetMapping("/getArticleTotle")
    public ResponseResult getArticleTotle(){
        return articleService.getArticleTotle();
    }

    @SystemLog(BusinessName = "获取用户总数")
    @GetMapping("/getUserTotle")
    public ResponseResult getUserTotle(){
        return userService.getUserTotle();
    }

    @SystemLog(BusinessName = "获取文章总浏览量")
    @GetMapping("/getArticleBroTotle")
    public ResponseResult getArticleBroTotle(){
       return  articleService.getArticleBroTotle();
    }

    @SystemLog(BusinessName = "获取新增文章总数")
    @GetMapping("/getNewArticlesTotle")
    public ResponseResult getNewArticlesTotle(){
        return articleService.getNewArticlesTotle();
    }

    @SystemLog(BusinessName = "获取每日用户增加")
    @GetMapping("/getUserDailyadd")
    public ResponseResult getUserDailyadd(){
       return userService.getUserCount();
    }


    @SystemLog(BusinessName = "获取每日文章增加")
    @GetMapping("/getNewDailyArticles")
    public  ResponseResult getNewDailyArticles(){
       return articleService.getNewDailyArticles();
    }

    @SystemLog(BusinessName = "获取草稿文章列表")
    @GetMapping("/getDraft")
    public  ResponseResult getDraft(){
        return articleService.getDraft();
    }
    @SystemLog(BusinessName = "审核通过")
    @GetMapping("/passArticle")
    public ResponseResult passArticle(Long id){

        return articleService.passArticle(id);
    }
    @SystemLog(BusinessName = "审核通过")
    @GetMapping("/deleteArticle")
    public ResponseResult deleteArticle(Long id){
        //用户前台显示是否被驳回 草稿等分类 文章状态详细分类
        return articleService.deleteArticle(id);
    }


}
