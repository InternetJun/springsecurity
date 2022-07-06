package com.example.util.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class SecurityException extends OAuth2Exception {

    public SecurityException(String msg, Throwable e) {
        super(msg, e);

    }

    public SecurityException(String msg) {
        super(msg);

    }

    public String getOAuth2ErrorCode() {
        return null;
    }

    public int getHttpErrorCode() {
        return Integer.MAX_VALUE;
    }


}
