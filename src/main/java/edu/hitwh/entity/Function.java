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

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer state;

    @TableField("parentId")
    private Long parentId;

    @TableField("isLeaf")
    private Boolean isLeaf;

    @TableField("`order`")
    private Integer order;

    private String url;

    @TableField("serviceId")
    private Long serviceId;

}
