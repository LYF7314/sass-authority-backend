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
@TableName("frame_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    // 角色id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 角色名称
    private String name;
    // 租户id
    @TableField("tenantId")
    private Long tenantId;
    // 角色描述
    private String description;
}
