package com.blog.controller.admin.restController;

import com.blog.annotation.SystemLog;
import com.blog.commonResult.ResponseResult;
import com.blog.pojo.SysMenu;
import com.blog.pojo.SysRole;
import com.blog.service.SysMenuService;
import com.blog.service.SysRoleMenuService;
import com.blog.service.SysRoleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin_roleAndMenu')")
public class RoleAndMenuRestController {
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @SystemLog(BusinessName = "获取角色列表分页")
    @GetMapping("/getRolePages")
    public ResponseResult getRolePages(@NotNull Long Rpage,Long Mpage,@NotEmpty int size){
        return  sysRoleService.getRoleLest(Rpage,size);
    }
    @SystemLog(BusinessName = "通过id获取role")
    @GetMapping("/getRolebyid")
    public ResponseResult getRolebyid(Long id){
        return ResponseResult.okResult(sysRoleService.getById(id));
    }
    @SystemLog(BusinessName = "禁用角色")
    @GetMapping("/stopRole")
    public ResponseResult stopRole(@NotNull Long id){
        SysRole byId = sysRoleService.getById(id);
        byId.setStatus("1");
        return sysRoleService.updateRole(byId);
    }
    @SystemLog(BusinessName = "恢复角色")
    @GetMapping("/renewRole")
    public ResponseResult renewRole(@NotNull Long id){
        SysRole byId = sysRoleService.getById(id);
        byId.setStatus("0");
        return sysRoleService.updateRole(byId);
    }
    @SystemLog(BusinessName = "更新角色")
    @PostMapping("/updateRole")
    public ResponseResult updateRole(@Valid SysRole role){
        return sysRoleService.updateRole(role);
    }

    @SystemLog(BusinessName = "获取角色权限")
    @GetMapping("/getRoleMenus")
    public ResponseResult getRoleMenus(@Valid Long id){
        return ResponseResult.okResult(sysRoleMenuService.getRoleMenuByRoleId(id));
    }

    @SystemLog(BusinessName = "删除角色")
    @GetMapping("/deleteRole")
    public ResponseResult deleteRole(Long id){

        return sysRoleService.deleteRole(id);
    }
    @SystemLog(BusinessName = "通过id获取menu")
    @GetMapping("/getMenubyid")
    public ResponseResult getMenubyid(Long id){
        return ResponseResult.okResult(sysMenuService.getById(id));
    }

    @SystemLog(BusinessName = "禁用权限")
    @GetMapping("/stopMenu")
    public ResponseResult stopMenu(@NotNull Long id){
        SysMenu byId = sysMenuService.getById(id);
        byId.setStatus("1");
        return  sysMenuService.updateMenus(byId);
    }
    @SystemLog(BusinessName = "恢复权限")
    @GetMapping("/renewMenu")
    public ResponseResult renewMenu(@NotNull Long id){
        SysMenu byId = sysMenuService.getById(id);
        byId.setStatus("0");
        return  sysMenuService.updateMenus(byId);
    }
    @SystemLog(BusinessName = "删除角色")
    @GetMapping("/deleteMenu")
    public ResponseResult deleteMenu(Long id){
        return sysMenuService.deleteMenus(id);
    }
    @SystemLog(BusinessName = "更新权限")
    @PostMapping("/updateMenu")
    public ResponseResult updateMenu(SysMenu sysMenu){
      return   sysMenuService.updateMenus(sysMenu);
    }
}
