package com.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Email;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@TableName("sys_user")
public class User  {
    //主键@TableId
    private Long id;

    //用户名
    @NotNull
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    private String type;
    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    @Email
    private String email;
    //手机号
    @Length(min = 11)
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    private String sex;
    //头像
    private String avatar;
    //创建人的用户id
    private Long createBy;
    //创建时间
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新时间
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}
