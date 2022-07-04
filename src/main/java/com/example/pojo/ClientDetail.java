package com.example.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@TableName("oauth_client_details")
@Data
public class ClientDetail extends Model<ClientDetail> {
    private String clientId;
    private String resource_ids;
    private String client_secret;
    private String scope;
//    authorized_grant_types;
//    web_server_redirect_uri;

}