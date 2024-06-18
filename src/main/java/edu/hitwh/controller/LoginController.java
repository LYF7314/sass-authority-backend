package edu.hitwh.controller;

import edu.hitwh.dto.LoginDTO;
import edu.hitwh.entity.User;
import edu.hitwh.service.IFrameFunctionService;
import edu.hitwh.service.IFrameTenantService;
import edu.hitwh.service.IFrameUserService;
import edu.hitwh.utils.RedisConstants;
import edu.hitwh.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_KEY;

/*
 * @Author lyf
 * @Description Login and related APIs
 * @Date 10:54 2024/6/8
 **/
@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {

    @Resource
    private IFrameUserService frameUserService;

    @Resource
    private IFrameTenantService frameTenantService;

    @Resource
    private IFrameFunctionService frameFunctionService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginInfo,HttpServletRequest request) {
        log.info("login: {}",loginInfo.toString());
        User user = request.getSession(false)!=null?(User)(request.getSession(false).getAttribute(RedisConstants.LOGIN_INFO_KEY)):null;
        if(user != null) log.info("login session: {}",user.getId());
        request.getSession().invalidate();
        user = frameUserService.login(loginInfo);
        if(user != null){
            request.getSession(true).setAttribute(LOGIN_INFO_KEY, user);
            return Result.ok("登录成功");
        }else{
            return Result.fail("登录失败");
        }
    }

    @GetMapping("/logout")
    public Result logout( HttpServletRequest request) {
        return frameUserService.logout(request);
    }

    @GetMapping("/tenants")
    public Result getTenants() {
        return frameTenantService.getAllTenantDTOs();
    }

    @GetMapping("/userinfo")
    public Result getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(LOGIN_INFO_KEY) == null) {
            return Result.ok("Session not exist");
        }
        return frameUserService.getUserInfo(request);
    }

    @GetMapping("/navigation")
    public Result getNavigation(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(LOGIN_INFO_KEY) == null) {
            return Result.ok("Session not exist");
        }
        return frameFunctionService.getNavigation(request);
    }

}
