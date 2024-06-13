package edu.hitwh.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * <p>
 *
 * </p>
 *
 * @author lyf hhb
 * @since 2024-06-13
 */
@Data
@AllArgsConstructor
@TableName("frame_userfunctionview")
public class UserFunctionView {

    public static final Integer STATE_AVAILABLE = 1;

    public static final Integer STATE_UNAVAILABLE = 1;

    private static final long serialVersionUID = 1L;

    @TableField("userId")
    Long userId;

    @TableField("functionId")
    Long functionId;

    String name;

    private Integer state;

    @TableField("`order`")
    Integer order;

    @TableField("parentId")
    private Long parentId;

    @TableField("isLeaf")
    private Boolean isLeaf;

    private String url;

    @TableField("serviceId")
    private Long serviceId;

}
