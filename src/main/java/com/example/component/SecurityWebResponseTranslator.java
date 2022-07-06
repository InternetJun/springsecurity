package com.example.component;


import com.example.common.OauthAccessConstant;
import com.example.util.exception.SecurityException;
import com.example.util.exception.ServerErrorException;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.MethodNotAllowedException;


@Component
public class SecurityWebResponseTranslator implements WebResponseExceptionTranslator{

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    @SneakyThrows
    public ResponseEntity translate(Exception e) throws Exception {
        Throwable[] causes = throwableAnalyzer.determineCauseChain(e);
        Exception ase = (AuthenticationException)throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,causes);
        if (ase != null) {
            return handeOAuth2Exception(new UnauthorizedClientException(ase.getMessage(), ase));
        }

        ase = (OAuth2Exception)throwableAnalyzer.getFirstThrowableOfType(
                OAuth2Exception.class, causes
        );
        if (ase != null) {
            return handeOAuth2Exception((OAuth2Exception)ase);
        }

        ServerErrorException serverErrorException = new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return handeOAuth2Exception(serverErrorException);
    }

    private ResponseEntity<OAuth2Exception> handeOAuth2Exception(OAuth2Exception e){
        int status=e.getHttpErrorCode();
        HttpHeaders headers=new HttpHeaders();
        headers.set(HttpHeaders.CACHE_CONTROL,"no-store");
        headers.set(HttpHeaders.PRAGMA,"no-cache");
        if(status== HttpStatus.UNAUTHORIZED. value() || (e instanceof InsufficientScopeException)){
            headers.set(HttpHeaders.WWW_AUTHENTICATE,
                    String.format("%s %s", OAuth2AccessToken.BEARER_TYPE,e.getSummary()));
        }
        //客户端异常直接返回客户端，不然无法解析
        if(e instanceof ClientAuthenticationException){
            return new ResponseEntity<>(e, headers,
                    HttpStatus.valueOf(status));
        }
        return new ResponseEntity(new SecurityException(e.getMessage(),e), headers,
                HttpStatus.valueOf(status));
    }
}
