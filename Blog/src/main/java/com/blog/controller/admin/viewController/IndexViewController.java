package com.blog.controller.admin.viewController;

import com.blog.commonResult.ResponseResult;
import com.blog.service.ArticleService;
import com.blog.service.SysMenuService;
import com.blog.service.SysRoleService;
import com.blog.service.UserService;
import com.blog.vo.ArticleListVo;
import com.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * 后台主页页面跳转控制器
 */
@Controller
@PreAuthorize("hasAuthority('admin_basic')")
@RequestMapping("/admin")
public class IndexViewController {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping("/")
    public String index(Model model) {
        common(model);
        ResponseResult draft = articleService.getDraft();
        List<ArticleListVo> data = (List<ArticleListVo>) draft.getData();
        model.addAttribute("articleListVo",data);
        return "admin/index";
    }

    @RequestMapping("/ui_buttons")
    @PreAuthorize("hasAuthority('admin_buttons')")
    public String toButtons(Model model) {
        common(model);
        return "admin/ui_buttons";
    }

    @RequestMapping("/pages_profile")
    @PreAuthorize("hasAuthority('admin_buttons')")
    public String toprofile(Model model) {
        common(model);
        return "admin/pages_profile";
    }

    @RequestMapping("/pages_edit_pwd")
    @PreAuthorize("hasAuthority('admin_buttons')")
    public String toeditpwd(Model model) {
        common(model);
        return "admin/pages_edit_pwd";
    }

    @RequestMapping("/role_menu")
    // role 和 menu page页码不同
    public String toRoleMenu(Long page,Long mpage,int size,int RorM, Model model){
        common(model);
        model.addAttribute("roleList",sysRoleService.getRoleLest(page, size).getData());
        model.addAttribute("menuList",sysMenuService.getMenuLest(mpage,size).getData());
        model.addAttribute("RorM",RorM);
        return "admin/role_menu";
    }
    private void common(Model model){
        ResponseResult responseResult = userService.userInfo();
        UserInfoVo vo  = (UserInfoVo) responseResult.getData();
        model.addAttribute("headerImg",vo.getAvatar());
        model.addAttribute("userInfo",vo);
    }
}
