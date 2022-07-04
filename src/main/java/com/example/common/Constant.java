package com.example.common;

public interface Constant {

    public String OAUTH2_PREFIX = "oauth2:";

    public String CLIENT_KEY = OAUTH2_PREFIX + "client:details";

    public String CLIENT_FIELD = " client_id, resource_ids, concat('{noop}',client_secret) as client_secret, scope, " +
            "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity," +
            " refresh_token_validity, additional_information, autoapprove";

    public String BASE_FIND_STATEMENT = "select" +  CLIENT_FIELD + " from oauth_client_details";

    String DEFAULT_FIND = BASE_FIND_STATEMENT + " order by client_id";

    /*条件查询client_id*/
    String DEFAULT_SELECT_ID = BASE_FIND_STATEMENT + " where client_id = ?";
}
