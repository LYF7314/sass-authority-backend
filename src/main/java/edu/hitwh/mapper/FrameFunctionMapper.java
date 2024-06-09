package edu.hitwh.mapper;

import edu.hitwh.entity.Function;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
@Mapper
public interface FrameFunctionMapper extends BaseMapper<Function> {

    @Select("SELECT * FROM frame_function WHERE parentId = #{parentId} ORDER BY 'order'")
    List<Function> selectByParentId(Long parentId);

}
