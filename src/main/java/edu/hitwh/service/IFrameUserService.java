package edu.hitwh.service;

import edu.hitwh.dto.LoginDTO;
import edu.hitwh.entity.User;
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
public interface IFrameUserService extends IService<User> {

    Result login(LoginDTO loginInfo, HttpServletRequest request);

    Result logout(HttpServletRequest request);

    Result getUserInfo(HttpServletRequest request);

}
