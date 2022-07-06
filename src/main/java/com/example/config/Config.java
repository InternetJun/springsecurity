package com.example.config;

import com.example.fliter.LoginFilter;
//import com.example.fliter.VerificationCodeFilter;
import com.example.model.RespBean;
import com.example.oauth.UserNameAuthenticationProvider;
import com.example.pojo.User;
import com.example.service.UserDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import java.io.PrintWriter;

@Configuration
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class Config extends WebSecurityConfigurerAdapter {

    //    @Autowired
//    private MyAccessDeniedHandler myAccessDeniedHandler;
//    @Autowired
//    VerificationCodeFilter verificationCodeFilter;

    //    @Autowired
//    private PersistentTokenRepository persistentTokenRepository;
//    @Autowired
//    private DataSource dataSource;

    /**
     * 对用户密码的保护，有一定的算法。
     * @return
     */
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 如果是连接数据库查询的话使用这行代码，
        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 全部放行
//        http.authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .and().csrf().disable();
//    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //对防火的关闭和跨域的实现。
        http.cors()
            .and()
            .csrf().disable();
//        HttpSecurity httpSecurity = http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class);
        //自定义提供授权！
//        http.authenticationProvider(new UserNameAuthenticationProvider())
//                .addFilterAfter(loginFilter(), LoginFilter.class);

        http.authorizeRequests()
                .antMatchers("/login", "/verifyCode", "/doLogin", "/oauth/*")
                .permitAll()
                // 配置跨域问题和
//                .antMatchers("/oauth/*", "/oauth/token")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main.html")
                .permitAll()
                .and()
                .csrf().disable();

//            .authorizeRequests()
//            .antMatchers("/login.html","/login","/doLogin","/verifyCode", "/oauth/**", "/authentication/require")
//            .permitAll()
//            .anyRequest().authenticated();
//        http.csrf().disable();
//        //配置认证的问题                //用户访问其他URL资源都必须认证后访问，即登陆后访

//        http.formLogin()
//                .loginPage("/login.html")
//                .loginProcessingUrl("/login")
//                .successForwardUrl("/main.html")
//                .failureForwardUrl("/main1.html")
//                .permitAll()
//                .and()
//                .csrf().disable();
        //处理权限不足问题,没有登录的问题是该怎么解决的？
//        http.exceptionHandling()
//                .accessDeniedHandler(myAccessDeniedHandler);
//        http.authorizeRequests();
        //记住我
//        http.rememberMe()
//                .userDetailsService(userDetailServiceImpl)
//                .tokenRepository(persistentTokenRepository);
    }

    //静态资源配置,swagger测试类，登录注册的一些接口
    @Override
    public void configure(WebSecurity web) throws Exception {
        //swagger2所需要用到的静态资源，允许访问
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html");
        web.ignoring().antMatchers("/css/**", "/js/**", "/swagger-ui/**", "/v3/api-docs", "/img/**", "/fonts/**"
                , "/favicon.ico", "/verifyCode", "/user/register", "/login.html");

    }

//    @Bean
//     LoginFilter loginFilter() throws Exception {
//        LoginFilter loginFilter = new LoginFilter();
//        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
//                    response.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    User hr = (User) authentication.getPrincipal();
//                    hr.setPassword(null);
//                    RespBean ok = RespBean.ok("登录成功!", hr);
//                    String s = new ObjectMapper().writeValueAsString(ok);
//                    out.write(s);
//                    out.flush();
//                    out.close();
//                }
//        );
//        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
//                    response.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    RespBean respBean = RespBean.error(exception.getMessage());
//                    if (exception instanceof LockedException) {
//                        respBean.setMsg("账户被锁定，请联系管理员!");
//                    } else if (exception instanceof CredentialsExpiredException) {
//                        respBean.setMsg("密码过期，请联系管理员!");
//                    } else if (exception instanceof AccountExpiredException) {
//                        respBean.setMsg("账户过期，请联系管理员!");
//                    } else if (exception instanceof DisabledException) {
//                        respBean.setMsg("账户被禁用，请联系管理员!");
//                    } else if (exception instanceof BadCredentialsException) {
//                        respBean.setMsg("用户名或者密码输入错误，请重新输入!");
//                    }
//                    out.write(new ObjectMapper().writeValueAsString(respBean));
//                    out.flush();
//                    out.close();
//                }
//        );
//        loginFilter.setAuthenticationManager(authenticationManagerBean());
//        loginFilter.setFilterProcessesUrl("/login");
//        ConcurrentSessionControlAuthenticationStrategy sessionStrategy =
//                new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
//        sessionStrategy.setMaximumSessions(1);
//        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
//        return loginFilter;
//    }

    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
