package com.blog.controller.admin.restController;

import com.blog.commonResult.ResponseResult;
import com.blog.pojo.User;
import com.blog.service.SysUserRoleService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/management")
@PreAuthorize("hasAuthority('admin_managerement')")
public class AdminManagement {
    @Autowired
    UserService userService;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @GetMapping("/adlist")
    public ResponseResult getAdminList(String isAdmin,Long page, int size) {
        return userService.getAdmins(isAdmin,page, size);
    }

    @PostMapping("/updateAdmin")
    public ResponseResult updateAdmin(User user) {
        return userService.updateAdmins(user);
    }

    @RequestMapping("/deleteAdmin")
    public ResponseResult deleteAdmin(Long id) {
        return userService.deleteAdmins(id);
    }

    @GetMapping("/getAdminRoles")
    public ResponseResult getAdminRoles(Long id)
    {
        return  ResponseResult.okResult( sysUserRoleService.getSysRoleByUserId(id));
    }
    @GetMapping("/stopAdmin")
    public ResponseResult stopAdmin(Long id){
        User byId = userService.getById(id);
        byId.setStatus("1");
        return userService.updateAdmins(byId);
    }
    @GetMapping("/reNewAdmin")
    public ResponseResult reNewAdmin(Long id){
        User byId = userService.getById(id);
        byId.setStatus("0");
        return userService.updateAdmins(byId);
    }
    @GetMapping("/getAdminById")
    public ResponseResult getAdminById(Long id){
        return ResponseResult.okResult(userService.getById(id));
    }

}
