package edu.hitwh.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@ComponentScan(basePackages = "edu.hitwh.entity")
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private RedisSessionInterceptor redisSessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redisSessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/*");  // 登录接口不拦截
    }
}