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
@TableName("frame_service")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    // 服务id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    // 服务名称
    private String name;
    // 组件id
    @TableField("componentId")
    private Long componentId;
    // 开发者
    private String developer;
    // 服务url
    private String url;
    // 服务描述
    private String description;
}
