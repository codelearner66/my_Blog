package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.commonResult.ResponseResult;
import com.blog.mapper.SysMenuMapper;
import com.blog.pojo.SysMenu;
import com.blog.pojo.User;
import com.blog.service.SysMenuService;
import com.blog.vo.SysMenuVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SysMenuServiceImpl  extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<String> getUserAuthorityList(User user) {
        return null;
    }

    @Override
    /**
     * 获取权限列表
     */
    public ResponseResult getMenuLest() {

        return ResponseResult.okResult(list());
    }

    @Override
   public   ResponseResult getMenuByIds(Long...ids){
        List<SysMenu> list=new ArrayList<>();
        for (Long id : ids) {
            list.add(getById(id));
        }
        return  ResponseResult.okResult(list);
   }

    /**
     * 分页获取权限列表
     * @param pageId 页码
     * @param size   每页大小
     * @return
     */
    @Override
    public ResponseResult getMenuLest(Long pageId, int size) {
        Page<SysMenu> menuPage=new Page<>(pageId,size);
        page(menuPage);
        SysMenuVo sysMenuVo = new SysMenuVo();
        sysMenuVo.setNext(menuPage.hasNext());
        sysMenuVo.setPre(menuPage.hasPrevious());
        sysMenuVo.setList(menuPage.getRecords());
        long pageTotal= menuPage.getTotal()%size==0?menuPage.getTotal()/size:menuPage.getTotal()/size+1;
        sysMenuVo.setTotal(pageTotal);
        sysMenuVo.setCurrent(menuPage.getCurrent());
        return ResponseResult.okResult(sysMenuVo);
    }

    /**
     *  更新插入操作
     * @param sysMenu
     * @return
     */
    @Override
    public ResponseResult updateMenus(SysMenu sysMenu) {
        if (sysMenu.getId()==null) {
            return save(sysMenu)?ResponseResult.okResult(): ResponseResult.errorResult(553,"权限新建出错！");
        }
        return updateById(sysMenu)?ResponseResult.okResult(): ResponseResult.errorResult(554,"权限更新出错！");
    }

    @Override
    public ResponseResult deleteMenus(Long id) {
       return removeById(id)? ResponseResult.okResult():ResponseResult.errorResult(551,"删除权限出错！");

    }
}
