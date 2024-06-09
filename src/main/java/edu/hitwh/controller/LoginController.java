package edu.hitwh.controller;

import edu.hitwh.dto.LoginDTO;
import edu.hitwh.service.IFrameFunctionService;
import edu.hitwh.service.IFrameTenantService;
import edu.hitwh.service.IFrameUserService;
import edu.hitwh.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_KEY;

/*
 * @Author lyf
 * @Description Login and related APIs
 * @Date 10:54 2024/6/8
 **/
@RestController
public class LoginController {

    @Resource
    private IFrameUserService frameUserService;

    @Resource
    private IFrameTenantService frameTenantService;

    @Resource
    private IFrameFunctionService frameFunctionService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginInfo, HttpServletRequest request) {
        return frameUserService.login(loginInfo, request);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        return frameUserService.logout(request);
    }

    @GetMapping("/tenants")
    public Result getTenants() {
        return frameTenantService.getAllTenantDTOs();
    }

    @GetMapping("/userinfo")
    public Result getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(LOGIN_INFO_KEY) == null) {
            return Result.unLogin("Session not exist");
        }
        return frameUserService.getUserInfo(request);
    }

    @GetMapping("/navigation")
    public Result getNavigation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(LOGIN_INFO_KEY) == null) {
            return Result.unLogin("Session not exist");
        }
        return frameFunctionService.getNavigation(request);
    }

}
