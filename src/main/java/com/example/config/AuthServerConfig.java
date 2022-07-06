package com.example.config;


import com.example.common.Constant;
import com.example.component.SecurityWebResponseTranslator;
import com.example.pojo.User;
import com.example.service.ClientDetailServer;
import com.example.service.UserDetailServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务器的配置内容
 */
@Configuration
@EnableAuthorizationServer

//资源配置。
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    private AuthenticationManager authenticationManager;
    private SecurityWebResponseTranslator securityWebResponseTranslator;

    @Test
    public void main() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

//    @Bean
//    AuthorizationServerTokenServices tokenServices() {
//        DefaultTokenServices services = new DefaultTokenServices();
//        services.setClientDetailsService(new ClientDetailServer(dataSource));
//        services.setSupportRefreshToken(true);
//        services.setTokenStore(tokenStore);
//
//        return services;
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //需要一个重写，支持一个redis的缓存数据。没有去查数据库导致的问题！
        ClientDetailServer clientDetailsService = new ClientDetailServer(dataSource);
        clientDetailsService.setSelectClientDetailsSql(Constant.DEFAULT_SELECT_ID);
        clientDetailsService.setFindClientDetailsSql(Constant.DEFAULT_FIND);
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {

        // 允许 /oauth/token的端点表单认证
        oauthServer.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                // 允许 /oauth/token_check端点的访问
                .checkTokenAccess("permitAll()")
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        // 密码加密
                        return null;
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        // 密码校验
                        // return false;
                        return true;
                    }
                })
                .allowFormAuthenticationForClients();
    }
//  @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.checkTokenAccess("permitAll()");
//    }
//    @Override
//    public void configure(AuthorizationServerEndpointsConfiguration endpointsConfiguration) {
//    }

    //    tokenstore
    @Bean
    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(new ClientDetailServer(dataSource));
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore());
        services.setAccessTokenValiditySeconds(60 * 60 * 2);
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3);
        return services;
    }
    // tokenEnhancer

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .reuseRefreshTokens(false)
                .exceptionTranslator(securityWebResponseTranslator);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return ((accessToken, authentication) ->  {
            final Map<String, Object> additionalInfo = new HashMap<>();
            Authentication userAuthentication = authentication.getUserAuthentication();
            User user = (User)userAuthentication.getPrincipal();
            additionalInfo.put("userName", user.getUsername());
            additionalInfo.put("telephone", user.getPhone());
            ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        });
    }

    // 授权码一次就失效！！
    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(Constant.OAUTH2_PREFIX);
        return tokenStore;
    }
}
