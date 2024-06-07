package edu.hitwh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.hitwh.dto.LoginDTO;
import edu.hitwh.service.IFrameUserService;
import edu.hitwh.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @Resource
    private IFrameUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    Result login(@RequestBody LoginDTO loginInfo) throws JsonProcessingException {
        return userService.login(loginInfo);
    }

}