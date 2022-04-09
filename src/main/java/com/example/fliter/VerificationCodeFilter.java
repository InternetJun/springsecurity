//package com.example.fliter;
//
//import com.example.model.RespBean;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Component
//public class VerificationCodeFilter extends GenericFilter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        if ("POST".equals(request.getMethod())
//                && "/dologin".equals("verify_code")){
//            String code = request.getParameter("code");
//            String verify_code = (String)request.getSession().getAttribute("verify_code");
//            if(code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())) {
//                //验证错误
//                response.setContentType("application/json;charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.write(new ObjectMapper().writeValueAsString(RespBean.error("验证码错误")));
//                out.flush();
//                out.close();
//                return;
//            } else {
//                filterChain.doFilter(request, response);
//            }
//        }
//    }
//}
