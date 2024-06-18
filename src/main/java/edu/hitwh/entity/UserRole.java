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
@TableName("frame_userrole")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    //用户-功能关系id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 用户id
    @TableField("userId")
    private Long userId;
    // 角色id
    @TableField("roleId")
    private Long roleId;
}
