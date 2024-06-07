package edu.hitwh.mapper;

import edu.hitwh.entity.Function;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
public interface FrameFunctionMapper extends BaseMapper<Function> {

    @Select("SELECT * FROM frame_function WHERE parentId = #{parentId}")
    List<Function> selectByParentId(Long parentId);

}
