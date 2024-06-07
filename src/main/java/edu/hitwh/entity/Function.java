package edu.hitwh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("frame_function")
public class Function implements Serializable {

    public static final Integer STATE_AVAILABLE = 1;

    public static final Integer STATE_UNAVAILABLE = 1;

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer state;

    private Long parentId;

    @TableId(value = "isleaf")
    private Integer isLeaf;

    private Integer order;

    private String url;

    private Long serviceId;

}
