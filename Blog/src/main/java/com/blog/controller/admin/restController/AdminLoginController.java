package com.blog.controller.admin.restController;

import com.blog.commonResult.ResponseResult;
import com.blog.pojo.User;
import com.blog.service.BlogLoginService;
import com.blog.utils.IdentCodeUtils;
import com.blog.vo.BlogUserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminLoginController {
    @Autowired
    BlogLoginService blogLoginService;
    @RequestMapping("/getIdentCode")
    public void getInentCode(HttpServletResponse response) throws IOException {
        IdentCodeUtils identCode = new IdentCodeUtils();
        BufferedImage image = identCode.getImage();
        Cookie userCookie=new Cookie("keyCookie",identCode.getText());
        //todo 优化为存入redis 以生成的随机字符为键 或者定义一个静态set
        //设置cookie的最大生命周期为一周
        userCookie.setMaxAge(60*3);
        //设置路径为全路径（这样写的好处是同一项目下的页面都可以访问该cookie）
        userCookie.setPath("/");
        //response是HttpServletResponse类型
        response.addCookie(userCookie);

        IdentCodeUtils.output(image, response.getOutputStream());
    }
    @PostMapping("/adminLogin")
    public ResponseResult adminLogin(@Valid User user, String captcha, String key,HttpServletRequest request,HttpServletResponse response){
        //校验 验证码v
        IdentCodeUtils identCode = new IdentCodeUtils();
       switch(identCode.check(captcha,key)){
           case 1:{
               return ResponseResult.errorResult(520,"验证码过期!请点击刷新...");
           }
           case 3:{
               return ResponseResult.errorResult(521,"验证码错误!请点击刷新...");
           }
           default:{
               //用户登录验证
               //移除用户cookie
               Cookie[] cookies = request.getCookies();
               for (Cookie cookie : cookies) {
                   if ("keyCookie".equals(cookie.getName())) {
                       // 删除 cookie
                       cookie.setMaxAge(0);
                       //路径一定要写上，不然销毁不了
                       cookie.setPath("/");
                       response.addCookie(cookie);
                   }
               }
               ResponseResult login = blogLoginService.login(user);
               BlogUserLoginVo vo= (BlogUserLoginVo) login.getData();
               Cookie cooki=new Cookie("token_cookie",vo.getToken());
               cooki.setMaxAge(1000*60*24*7);
               cooki.setPath("/");  //路径一定要写上，不然销毁不了
               response.addCookie(cooki);
               return login;
           }
       }
    }
}
