package edu.hitwh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("frame_function")
public class Function implements Serializable {

    public static final Integer STATE_AVAILABLE = 1;

    public static final Integer STATE_UNAVAILABLE = 1;

    @Serial
    private static final long serialVersionUID = 1L;
    // 功能id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 功能名称
    private String name;
    // 功能描述
    private Integer state;
    // 父功能id
    @TableField("parentId")
    private Long parentId;
    // 是否叶子节点
    @TableField("isLeaf")
    private Boolean isLeaf;
    // 功能顺序
    @TableField("`order`")
    private Integer order;
    // 功能url
    private String url;
    // 服务id、、
    @TableField("serviceId")
    private Long serviceId;

}
