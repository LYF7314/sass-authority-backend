package edu.hitwh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.hitwh.dto.LoginDTO;
import edu.hitwh.service.IFrameUserService;
import edu.hitwh.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Resource
    private IFrameUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录用户，记录用户id至session
     * @param loginInfo 登录信息
     * @return 成功信息
     * @throws JsonProcessingException
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginInfo) throws JsonProcessingException {
        return userService.login(loginInfo);
    }

    /**
     * 登出用户，清除session信息
     * @return 成功信息
     */
    @GetMapping("/logout")
    public Result logout() {
        return Result.ok();
    }

    /**
     * 获取用户权限用于显示导航栏
     * @return 用户权限树
     */
    @GetMapping("/navigation")
    public Result getNavigation() {
        return Result.fail("Not implemented");
    }

    /**
     * 获取租户列表用于显示可登录的租户
     * @return 租户名称列表
     */
    @GetMapping("/tenants")
    public Result getTenants() {
        return Result.fail("Not implemented");
    }

    /**
     * 使用session中记录的登录用户id获取用户信息
     * @return 用户信息
     */
    @GetMapping("/userinfo")
    public Result getUserInfo() {
        return Result.fail("Not implemented");
    }
}
