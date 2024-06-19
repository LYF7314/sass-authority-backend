package edu.hitwh.config;

import edu.hitwh.entity.User;
import edu.hitwh.utils.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_KEY;
@Slf4j
@Component
public class RedisSessionInterceptor implements HandlerInterceptor {
    @Resource
    HttpSession session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (session != null && session.getAttribute(LOGIN_INFO_KEY) != null) {
            User user = (User)(session.getAttribute(RedisConstants.LOGIN_INFO_KEY));
            log.info("interceptor session: {}",user.getId());
            return true;
        }
        log.info(session == null ? "session is null" : "session is not null");
        log.info("request ip: {}",request.getRemoteAddr());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":2,\"msg\":\"用户未登录\"}");
        return false;
    }
}
