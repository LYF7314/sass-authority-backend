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

    // 用户id
    @TableField("userId")
    Long userId;
    // 功能id
    @TableField("functionId")
    Long functionId;
    // 功能名称
    String name;
    // 功能状态
    private Integer state;
    // 功能排序
    @TableField("`order`")
    Integer order;
    // 父功能id
    @TableField("parentId")
    private Long parentId;
    // 是否叶子节点
    @TableField("isLeaf")
    private Boolean isLeaf;
    // 功能url
    private String url;
    // 服务id
    @TableField("serviceId")
    private Long serviceId;

}
