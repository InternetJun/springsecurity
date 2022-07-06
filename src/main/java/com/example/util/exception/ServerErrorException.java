package com.example.util.exception;

import cn.hutool.http.HttpStatus;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
public class ServerErrorException extends SecurityException{
    public ServerErrorException(String msg, Throwable t) {
        super(msg);
    }

    public String getOAuth2ErrorCode() {
        return "Server_error";
    }

    public int getHttpErrorCode() {
        return HttpStatus.HTTP_INTERNAL_ERROR;
    }
}
