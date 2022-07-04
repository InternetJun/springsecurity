package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class AccessTokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

//    @Bean
//    //用redis存储的设置，不要使用@Quality注解了
//    TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }
}
