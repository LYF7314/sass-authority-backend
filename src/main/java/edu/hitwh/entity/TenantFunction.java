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

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    @TableField("tenantId")
    private Long tenantId;

    @TableField("functionId")
    private Long functionId;

    private Integer order;

    public TenantFunction(Long tenantId,Long functionId) {
        this.tenantId = tenantId;
        this.functionId = functionId;
    }
}
