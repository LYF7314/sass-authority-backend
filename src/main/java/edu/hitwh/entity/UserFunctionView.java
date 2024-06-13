package edu.hitwh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.hitwh.utils.BooleanToIntegerDeserializer;
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

    Long userId;

    Long functionId;

    String name;

    private Integer state;

    Integer order;

    private Long parentId;

    @JsonDeserialize(using = BooleanToIntegerDeserializer.class)
    private Integer isLeaf;

    private String url;

    private Long serviceId;

}
