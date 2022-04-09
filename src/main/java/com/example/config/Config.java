package com.example.config;

import com.example.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
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



//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        HttpSecurity httpSecurity = http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class);
//        http.authorizeRequests()
//                //允许根路径url的访问
//                .antMatchers("/").permitAll()
//                //允许swagger-ui.html访问
//                .antMatchers("/swagger-ui.html").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .logout().permitAll()
//                .and()
//                .formLogin().permitAll();
//
//        //对防火的关闭
//        http.csrf().disable();
//
//        //处理权限不足问题,没有登录的问题是该怎么解决的？
////        http.exceptionHandling()
////                .accessDeniedHandler(myAccessDeniedHandler);
////        http.authorizeRequests();
//        //记住我
////        http.rememberMe()
////                .userDetailsService(userDetailServiceImpl)
////                .tokenRepository(persistentTokenRepository);
//    }

    //静态资源配置
    @Override
    public void configure(WebSecurity web) throws Exception {
        //swagger2所需要用到的静态资源，允许访问
        web.ignoring().antMatchers("/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html");
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");

    }

//    @Bean
//    LoginFilter loginFilter() throws Exception {
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
//        loginFilter.setFilterProcessesUrl("/doLogin");
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
