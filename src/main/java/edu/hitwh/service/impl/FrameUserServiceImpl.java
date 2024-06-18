package edu.hitwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.hitwh.dto.LoginDTO;
import edu.hitwh.dto.UserInfoDTO;
import edu.hitwh.entity.Role;
import edu.hitwh.entity.User;
import edu.hitwh.entity.UserRole;
import edu.hitwh.mapper.FrameRoleMapper;
import edu.hitwh.mapper.FrameTenantMapper;
import edu.hitwh.mapper.FrameUserMapper;
import edu.hitwh.mapper.FrameUserroleMapper;
import edu.hitwh.service.IFrameUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hitwh.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_KEY;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyf
 * @since 2024-06-06
 */
@Service
@Slf4j
public class FrameUserServiceImpl extends ServiceImpl<FrameUserMapper, User> implements IFrameUserService {

    @Resource
    private FrameUserMapper userMapper;

    @Resource
    private FrameUserroleMapper frameUserroleMapper;

    @Resource
    private FrameRoleMapper frameRoleMapper;

    @Resource
    private FrameTenantMapper tenantMapper;

    @Override
    public User login(LoginDTO loginInfo) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getTenantId, loginInfo.getTenantId())
                .eq(User::getUserName, loginInfo.getUserName())
                .eq(User::getPassword, loginInfo.getPassword()));
        return user;
    }

    @Override
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 没有会话则不创建新会话
        if (session != null) {
            session.invalidate(); // 使当前会话无效
            return Result.ok("logout successfully");
        } else {
            return Result.ok("No active session found");
        }
    }

    @Override
    public Result getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<String> userRoleNameList = new ArrayList<>();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        User user = (User)session.getAttribute(LOGIN_INFO_KEY);
        Long userId = user.getId();
        Long tenantId = user.getTenantId();
        // 根据用户名得到对应角色id的列表
        List<Long> userRoleIdList = frameUserroleMapper.selectList(new LambdaQueryWrapper<UserRole>()
                        .eq(UserRole::getUserId, userId))
                .stream()
                .map(UserRole::getRoleId)
                .toList();
        if (!userRoleIdList.isEmpty()){
            // 根据角色id的列表进一步得到角色名列表
            userRoleNameList = frameRoleMapper.selectBatchIds(userRoleIdList)
                    .stream()
                    .map(Role::getName)
                    .toList();
        }
        // 根据用户的所属租户id得租户name
        String tenantName = tenantMapper.selectById(tenantId).getName();
        userInfoDTO.setUserName(user.getUserName());
        userInfoDTO.setTenantName(tenantName);
        userInfoDTO.setRoleNames(userRoleNameList);
        return Result.ok(userInfoDTO);
    }
}
