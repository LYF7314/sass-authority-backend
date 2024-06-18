package edu.hitwh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
@Getter
@Setter
@TableName("frame_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    // 用户id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 账号
    private String account;
    // 密码
    private String password;
    // 用户名
    @TableField("userName")
    private String userName;
    // 租户id
    @TableField("tenantId")
    private Long tenantId;
    // 邮箱
    private String email;
    // 电话
    private String telephone;
}
