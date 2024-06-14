package edu.hitwh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class CookieConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setUseHttpOnlyCookie(false); // 关闭 HttpOnly 属性
        cookieSerializer.setCookiePath("/"); // 设置 Cookie 路径
//        cookieSerializer.setCookieMaxAge(24 * 60 * 60); // 设置 Cookie 过期时间为 1 天
        return cookieSerializer;
    }
}

