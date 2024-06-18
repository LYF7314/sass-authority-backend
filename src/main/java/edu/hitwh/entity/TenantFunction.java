package edu.hitwh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
@Data
@TableName("frame_tenantfunction")
@AllArgsConstructor
@NoArgsConstructor
public class TenantFunction implements Serializable {

    private static final long serialVersionUID = 1L;
    //租户-功能关系id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //功能名称
    private String name;
    //租户id
    @TableField("tenantId")
    private Long tenantId;
    //功能id
    @TableField("functionId")
    private Long functionId;
    //排序
    private Integer order;

    public TenantFunction(Long tenantId,Long functionId) {
        this.tenantId = tenantId;
        this.functionId = functionId;
    }
}
