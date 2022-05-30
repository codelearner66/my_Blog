package com.blog.controller.admin.restController;

import com.blog.commonResult.ResponseResult;
import com.blog.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class TempAuthorization {
    @Autowired
    SysUserRoleService sysUserRoleService;
      @PostMapping("/userAddRole")
    public ResponseResult userAddRole(@RequestParam(value = "userIds[]") Long [] userIds,@RequestParam(value = "roleId[]") Long[] roleId){
          return  sysUserRoleService.addUserRoleByUserId(userIds,roleId);
      }
}
