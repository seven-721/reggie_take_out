package com.seven.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.seven.reggie.dao")
@ServletComponentScan(basePackages = "com.seven.reggie.filter")
@EnableCaching  //开启注解缓存
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("启动成功!!");
    }
}
