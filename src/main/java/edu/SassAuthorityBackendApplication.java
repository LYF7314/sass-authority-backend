package edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableRedisHttpSession()
@SpringBootApplication
@MapperScan("edu.hitwh.mapper")
public class SassAuthorityBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SassAuthorityBackendApplication.class, args);
    }

}
