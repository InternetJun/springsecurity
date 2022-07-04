package com.example.config;

import com.example.fliter.LoginFilter;
import com.example.oauth.UserNameAuthenticationProvider;
import com.example.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

// 对用户名和密码的操作？在这之后有全新啊的放开。
//@Component
//public class UserNameAuthenticationConfig extends SecurityConfigurerAdapter<LoginFilter, HttpEntity> {
//
//    @Autowired
//    private UserDetailServiceImpl userDetailService;
//    @Autowired
//    // 验证码--》用户名，密码验证
//    private LoginFilter loginFilter;
///*    @Autowired
//    private */
//
//    public void config(HttpEntity http) throws Exception {
//        //设置provider
//        UserNameAuthenticationProvider userNameAuthenticationProvider = new UserNameAuthenticationProvider();
//        //http.authenticationProvider(userNameAuthenticationProvider)
//        ////                .addFilterAfter(loginFilter, LoginFilter.class);
//        http.authenticationProvider(userNameAuthenticationProvider)
//                .addFilterAfter(loginFilter, LoginFilter.class);
//    }
//}
