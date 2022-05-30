package com.blog.controller.admin.restController;

import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.service.AuthorizationService;
import com.blog.service.SysUserRoleService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/UserManagement")
@PreAuthorize("hasAuthority('admin_managerement')")
public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping("/deleteUser")
    public ResponseResult deleteAdmin(Long id) {
        return userService.lackUser(id);
    }

    @RequestMapping("/undeleteUser")
    public ResponseResult undeleteUser(Long id) {
        return userService.unlackUser(id);
    }

    @RequestMapping("/stopUser")
    public ResponseResult stopUser(Long id){
        return authorizationService.lockUser(id)? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @RequestMapping("reNewUser")
    public ResponseResult reNewUser(Long id){
        return authorizationService.unlockUser(id)? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
    @GetMapping("/getUserRoles")
    public ResponseResult getUserRoles(Long id)
    {
        return  ResponseResult.okResult( sysUserRoleService.getSysRoleByUserId(id));
    }
}
