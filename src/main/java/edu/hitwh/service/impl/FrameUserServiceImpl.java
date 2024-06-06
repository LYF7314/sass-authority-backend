package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.LoginDTO;
import edu.hitwh.entity.User;
import edu.hitwh.mapper.FrameUserMapper;
import edu.hitwh.service.IFrameUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
@Service
public class FrameUserServiceImpl extends ServiceImpl<FrameUserMapper, User> implements IFrameUserService {

    @Resource
    private FrameUserMapper userMapper;

    /*
     * @Author Liyifan
     * @Description login API
     * @Date 0:27 2024/6/7
     * @Param [loginInfo]
     * @return edu.hitwh.utils.Result
     **/
    @Override
    public Result login(LoginDTO loginInfo) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getTenantId, loginInfo.getTenantId())
                .eq(User::getUserName, loginInfo.getUsername())
                .eq(User::getPassword, loginInfo.getPassword()));
        if (user == null) {
            return Result.fail("User not found");
        }
        return Result.ok("login successfully");
    }
}
