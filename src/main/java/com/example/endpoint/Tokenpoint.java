
package com.example.endpoint;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.annotation.SecurityLog;
import com.example.common.OauthAccessConstant;
import com.example.model.RespBean;
import org.aspectj.weaver.patterns.ITokenSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class Tokenpoint {

    @Autowired
    //TODO REDIS自定义存储
//    @Qualifier("RedisTokenStore")
    private TokenStore tokenStore;
    @Autowired
    private CacheManager cacheManager;

    @DeleteMapping("/logout")
    public RespBean logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String header) {
        // hutool的判断
        if (StrUtil.isEmpty(header)) {
            return RespBean.error("退出失败，token为空");
        }

        String tokenValue = header.replace(OauthAccessConstant.BEARER_TYPE, "").trim();

        return removeToken(tokenValue);
    }

    @DeleteMapping("/token")
    private RespBean removeToken(String token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        // 对accessToken的判断
        if (accessToken == null && StrUtil.isBlank(accessToken.getValue())) {
            return RespBean.error("删除失败，token无效！");
        }
        Authentication accessToken1 = tokenStore.readAuthentication(accessToken);
        //缓存清除
        cacheManager.getCache("user_details").evict(accessToken1.getName());
        // redis
        tokenStore.removeAccessToken(accessToken);
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        // 不能刷新token数据
        tokenStore.removeRefreshToken(refreshToken);
        return RespBean.ok("ok");
    }

    // 发送日志处理。
    @SecurityLog
    public void securityLog(JSONObject jsonObject) {
        return;
    }

}
