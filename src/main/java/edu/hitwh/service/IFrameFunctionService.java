package edu.hitwh.service;

import edu.hitwh.entity.Function;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.hitwh.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
public interface IFrameFunctionService extends IService<Function> {

    Result createFunction(Function function);

    Result getAllFunctions();

    Result updateFunction(Function function);

    Result deleteFunction(Long id);

    Result getNavigation(HttpServletRequest request);
}
