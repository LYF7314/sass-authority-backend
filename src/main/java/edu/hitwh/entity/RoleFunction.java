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
@TableName("frame_rolefunction")
public class RoleFunction implements Serializable {

    private static final long serialVersionUID = 1L;
    // 角色-功能关系id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 角色id
    @TableField("roleId")
    private Long roleId;
    // 功能id
    @TableField("tenantFunctionId")
    private Long tenantFunctionId;
    // 排序
    private Integer order;
}
