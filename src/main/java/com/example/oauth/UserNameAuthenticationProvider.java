package com.example.oauth;

import com.example.service.UserDetailServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


public class UserNameAuthenticationProvider implements AuthenticationProvider {

    private UserDetailServiceImpl userDetailServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 对用户的授权:1，对token的获取。2，对用户名的验证，
        Object principal = authentication.getPrincipal();
        UserNameAuthenticationToken authenticationToken = (UserNameAuthenticationToken) principal;

        UserDetails user = userDetailServiceImpl.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("没有此用户信息！");
        }
        // 认证成功了，设置权限了。loginFliter只是对用户的登录，还有对应的权限的放开~~~
        UserNameAuthenticationToken authenticationResult = new UserNameAuthenticationToken(user.getUsername());
        authenticationResult.setDetails(authentication.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserNameAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
