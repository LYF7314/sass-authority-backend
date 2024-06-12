package edu.hitwh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
@TableName("frame_tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final List<Integer> stateList = List.of(0, 1);
    public static final List<Integer> typeList = List.of(0, 1);
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer code;

    private String name;

    private Integer state;

    private Integer type;

    private String email;

    private String telephone;

    private String address;

    private String description;

    @TableField("roleName")
    private String roleName;

    private String account;

    @TableField("userName")
    private String userName;
}
