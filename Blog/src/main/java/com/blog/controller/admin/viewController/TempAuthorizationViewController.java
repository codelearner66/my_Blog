package com.blog.controller.admin.viewController;

import com.blog.commonResult.ResponseResult;
import com.blog.service.SysMenuService;
import com.blog.service.SysRoleService;
import com.blog.service.UserService;
import com.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('admin_managerement')")
@RequestMapping("/admin")
public class TempAuthorizationViewController {
    @Autowired
    UserService userService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping("/TempAuthorization")
    public String tempAuthorization(String isAdmin,Long page,int size,Model model){
        common(model);
        model.addAttribute("roleList",sysRoleService.getRoleLest(page, size).getData());
        model.addAttribute("adminList",userService.getAdmins(isAdmin,page, size).getData());
        return "admin/TempAuthorization";
    }

    @RequestMapping("/TempAuthorizationPageturn")
    public String tempAuthorizationPageturn(Long page,Long mpage,int size, Model model){
        common(model);
        model.addAttribute("roleList",sysRoleService.getRoleLest(page, size).getData());
        model.addAttribute("adminList",userService.getAdmins(null,mpage, size).getData());
        return "admin/TempAuthorization";
    }
    private void common(Model model){
        ResponseResult responseResult = userService.userInfo();
        UserInfoVo vo  = (UserInfoVo) responseResult.getData();
        model.addAttribute("headerImg",vo.getAvatar());
        model.addAttribute("userInfo",vo);
    }
}
