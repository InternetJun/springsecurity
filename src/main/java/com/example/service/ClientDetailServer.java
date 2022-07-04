package com.example.service;

import com.example.common.Constant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

public class ClientDetailServer extends JdbcClientDetailsService {


    public ClientDetailServer(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 可缓存到redis；
     * @param clientId
     * @return
     */
    @Cacheable(value = Constant.CLIENT_KEY, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        return super.loadClientByClientId(clientId);
    }

}
