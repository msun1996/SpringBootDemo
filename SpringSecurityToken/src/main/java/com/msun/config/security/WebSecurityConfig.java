package com.msun.config.security;

import com.msun.config.security.handler.CustomAccessDeniedHandler;
import com.msun.config.security.handler.CustomLogoutSuccessHandler;
import com.msun.config.security.token.filter.JWTAuthenticationFilter;
import com.msun.config.security.token.filter.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启Security注解功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {
            // -- register url
            "/users/sighup",
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    // 登录验证，绑定自定义验证
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(myUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 关闭Session
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()  // 所有请求需要身份认证
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler) // 自定义访问失败处理器
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager())) // JWT拦截链 （用户登录成功会调用此方法，生成token，写入头）
                .addFilter(new JWTAuthenticationFilter(authenticationManager())) // JWT拦截器（校验token,用户和对应信息权限写入）
                .logout() // 默认注销行为为logout，可以通过下面的方式来修改
                .logoutSuccessUrl("/login")// 设置注销成功后跳转页面，默认是跳转到登录页面;
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll();
    }
}
