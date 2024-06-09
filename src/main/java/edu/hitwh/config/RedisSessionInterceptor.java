package edu.hitwh.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_KEY;

@Component
public class RedisSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(LOGIN_INFO_KEY) != null) {
            return true;
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":2,\"msg\":\"用户未登录\"}");
        return false;
    }

}
