package edu.hitwh.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import edu.hitwh.dto.InfoDTO;
import edu.hitwh.utils.InfoHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_KEY;
import static edu.hitwh.utils.RedisConstants.LOGIN_INFO_TTL;

@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            return true;
        }
        // 2.基于TOKEN获取redis中的用户
        String key  = LOGIN_INFO_KEY + token;
        Map<Object, Object> InfoMap = stringRedisTemplate.opsForHash().entries(key);
        // 3.判断用户是否存在
        if (InfoMap.isEmpty()) {
            return true;
        }
        // 5.将查询到的hash数据转为DTO
        InfoDTO infoDTO = BeanUtil.fillBeanWithMap(InfoMap, new InfoDTO(), false);
        // 6.存在，保存用户信息到 ThreadLocal
        InfoHolder.saveInfo(infoDTO);
        // 7.刷新token有效期
        stringRedisTemplate.expire(key, LOGIN_INFO_TTL, TimeUnit.MINUTES);
        // 8.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 移除用户
        InfoHolder.removeInfo();
    }
}
