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
public class UserManagementViewController {
    @Autowired
    UserService userService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping("/UserManagement")
    public String adminManagement(String isAdmin,Long page, int size, int aM, Model model){
        common(model);
        model.addAttribute("adminList",userService.getAdmins(isAdmin,page, size).getData());
        model.addAttribute("aM",aM);
        return "admin/userManagement";
    }
    @RequestMapping("/UserSerach")
    public String adminManagement(String isAdmin,String type,String msg,Model model){
        common(model);
        model.addAttribute("adminList",userService.adminSerach(isAdmin,type,msg).getData());
        model.addAttribute("aM",1);
        return "admin/userManagement";
    }
    private void common(Model model){
        ResponseResult responseResult = userService.userInfo();
        UserInfoVo vo  = (UserInfoVo) responseResult.getData();
        model.addAttribute("headerImg",vo.getAvatar());
        model.addAttribute("userInfo",vo);
    }
}
