package com.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.commonResult.ResponseResult;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.UserMapper;
import com.blog.pojo.User;
import com.blog.service.SysUserRoleService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.vo.AdminVo;
import com.blog.vo.UserCount;
import com.blog.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-02-09 00:28:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserMapper mapper;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult register(User user) {
       //ToDO 检验email是否唯一
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        user.setCreateTime(new Date());
        user.setNickName("小老弟");
        user.setUpdateTime(new Date());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> emailQuery = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        long count = count(queryWrapper);
        emailQuery.eq(User::getEmail, user.getEmail());
        long count1 = count(emailQuery);
        if (count > 0 || count1 > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        boolean save = save(user);
        if (save) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public User getUserByEmails(String mail) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, mail);
        UserMapper baseMapper = getBaseMapper();
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    /**
     * 更新用户信息
     * 通过条件判断与之前保存的不同才会进行更新
     */
    public ResponseResult updateUser(User user) {
        if (user == null) {
            throw new RuntimeException("用户为空");
        }
        User oldUser = SecurityUtils.getLoginUser().getUser();
        //todo 判断修改过的字段 进行更新
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", oldUser.getId());
        updateWrapper.set(user.getNickName() != null && !oldUser.getNickName().equals(user.getNickName()), "nick_name", user.getNickName())
                .set(user.getEmail() != null && !oldUser.getEmail().equals(user.getEmail()), "email", user.getEmail())
                .set(user.getSex() != null && !oldUser.getSex().equals(user.getSex()), "sex", user.getSex())
                .set(user.getAvatar() != null && !oldUser.getAvatar().equals(user.getAvatar()), "avatar", user.getAvatar());
        if (user.getPassword() != null && !"".equals(user.getPassword())) {
            String encode = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            updateWrapper.set(!oldUser.getPassword().equals(user.getPassword()), "password", user.getPassword());
        }
        boolean update = update(null, updateWrapper);
        if (update) {
            //todo 更新redis 中的数据
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult updatePassword(User user) {
        if (user == null) {
            throw new RuntimeException("用户为空");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId());
        if (!"".equals(user.getPassword())) {
            String encode = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            updateWrapper.set("password", user.getPassword());
        }
        boolean update = update(user, updateWrapper);
        if (update) {
            return ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    /**
     * 更新头像
     */
    public Boolean updateUserHeader(User user) {
        LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.<User>lambdaUpdate()
                .eq(User::getId, user.getId());
        return update(user, lambdaUpdate);
    }

    @Override
    public ResponseResult getUserTotle() {
        return ResponseResult.okResult(count());
    }

    @Override
    public ResponseResult getUserCount() {
        List<UserCount> usersCount = mapper.getUsersCount();
        //过滤掉日期为空的天
        List<UserCount> collect = usersCount.stream()
                .filter(userCount -> Objects.nonNull(userCount.getTime()))
                .distinct()
                .collect(Collectors.toList());
        return ResponseResult.okResult(collect);
    }

    @Override
    public ResponseResult getAdmins(String isAdmin,Long page, int size) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        if(!Objects.isNull(isAdmin)){
            queryWrapper.eq(User ::getType,isAdmin);
        }
        Page<User> page1=new Page<>(page, size);
        page(page1, queryWrapper);
        AdminVo adminVo=new AdminVo();
        adminVo.setList(page1.getRecords());
        adminVo.setCurrent(page1.getCurrent());
        adminVo.setNext(page1.hasNext());
        adminVo.setPre(page1.hasPrevious());
        adminVo.setTotal(page1.getTotal()%size==0?page1.getTotal()/size:page1.getTotal()/size+1);
        return ResponseResult.okResult(adminVo);
    }

    @Override
    public ResponseResult updateAdmins(User user) {
        if(user.getId()!=null&&user.getId() > 0){
            return updateById(user)?ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        else{
            return  save(user)?ResponseResult.okResult():ResponseResult.errorResult(620,"新增管理员出错！");
        }
    }

    @Override
    public ResponseResult deleteAdmins(Long id) {

        return removeById(id) ? ResponseResult.okResult() : ResponseResult.okResult();
    }

    @Override
    public ResponseResult lackUser(Long id) {
        User byId = getById(id);
        byId.setStatus("1");
        return  updateAdmins(byId);
    }

    @Override
    public ResponseResult unlackUser(Long id) {
        User byId = getById(id);
        byId.setStatus("0");
        return  updateAdmins(byId);
    }

    /**
     *  多种查询方式
     * @param type 查询类型 1 id查询 2 用户名模糊查询 default 混合查询
     * @param msg 用于查询的关键字
     * @return
     */
    @Override
    public ResponseResult adminSerach(String isAdmin,String type, String msg) {
     LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
     queryWrapper.eq(User::getType,isAdmin);
        List<User> users=new ArrayList<>();
      switch(type){
          case "1":{
               users.add(getById(msg));
               break;
          }
          case "2":{
              queryWrapper.like(User::getUserName,msg);
              users.addAll(mapper.selectList(queryWrapper));
              break;
          }
          default :{
              queryWrapper.and(userLambdaQueryWrapper -> userLambdaQueryWrapper.like(User::getUserName,msg)
                      .or()
                      .eq(User::getId,msg));

              users.addAll(mapper.selectList(queryWrapper));
          }
      }
        List<User> collect = users.stream()
                .distinct()
                .collect(Collectors.toList());
        AdminVo adminVo=new AdminVo();
        adminVo.setList(collect);
        adminVo.setCurrent(1L);
        adminVo.setNext(false);
        adminVo.setPre(false);
        adminVo.setTotal(1L);
        return ResponseResult.okResult(adminVo);
    }
}

