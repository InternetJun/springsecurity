package com.example.handle;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAccessDeniedHandler  implements AccessDeniedHandler {

    /*还有一个拦截器，全部要登录才能访问！！*/

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type","application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write("{\"status\":\"error\",\"msg\":\"forbidden\"}");
        printWriter.flush();
        printWriter.close();
    }
}
