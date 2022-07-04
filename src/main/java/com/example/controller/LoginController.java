package com.example.controller;

import com.example.model.RespBean;
import com.example.pojo.User;
import com.example.service.LoginService;
import com.example.service.UserDetailServiceImpl;
import com.example.utilConfig.VerificationCode;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

//    @GetMapping("/login")
//    public RespBean login(@RequestBody User user) {
//        return RespBean.error("未登录");
//    }

    @GetMapping("/verifyCode")
    // verify_code  verifyCode  verifyCode
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream());
    }

    @PostMapping("/user/register")
    public RespBean register(@RequestBody User user) {
        return loginService.register(user);
    }

    //用户在访问我们的项目时,如果需要身份认证,spring-security会根据
    //我在SecurityConfig中loginPage的配置跳转到我自定义的url即/authentication/require,
    //但在这个跳转之前spring-security会将我们的请求缓存到RequestCache的session里,
    //通过该类可以从session中再将缓存的请求信息拿出来
    private RequestCache requestCache = new HttpSessionRequestCache();

    //spring-security提供的类,可以方便的进行重定向
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping("/security/login")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        return "redirect:localhost:8080/security/login";
    }
}
