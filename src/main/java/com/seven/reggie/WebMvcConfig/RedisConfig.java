package com.seven.reggie.WebMvcConfig;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by IntelliJ IDEA.
 * Author:七 锦°
 * DATE:2023/1/9
 * Time:15:14
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //给key设置序列号器   set  p  java对象
        template.setKeySerializer(new StringRedisSerializer());
        //给value我也设置序列化起, value部分别人是有可能给你设置java对象，你需要把把java对象转换为json再设置的。
        //这时候有一个序列号器可以给你提供自动转换。
        template.setValueSerializer(new GenericFastJsonRedisSerializer());
        return template;
    }


}
