package com.example.oauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 对token的管理类
 */
public class UserNameAuthenticationToken extends AbstractAuthenticationToken {

    // 权限。
    private Object principal;

    UserNameAuthenticationToken(String username){
        // 刚开始没有任何权限数据。
        super(null);
        this.principal = username;
        this.setAuthenticated(false);
    }

    public UserNameAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException{
        if (isAuthenticated) {
            throw new IllegalArgumentException("can not trust the token");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials(){
        super.eraseCredentials();
    }
}
