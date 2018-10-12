package com.msun.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Bean
    UserDetailsService customUserService() {  // 注册UserDetailsService
        return new CustomUserService();
    }

    // 登录验证，绑定自定义验证
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                // 自定义可以访问界面
                // .antMatchers().permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/").permitAll()
                // 任意请求，登录后可以访问
                .anyRequest().authenticated()
                .and()
                // 登录页面用户可以任意访问
                .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                // 注销行为任意访问
                .logout().permitAll();
        httpSecurity.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 关闭CSRF验证
                .csrf().disable();
    }
    // 由于 httpSecurity.addFilterBefore 原因，此处static不拦截无法生效？
    // 防止静态文件被拦截(static默认配置不拦截)
    // httpSecurity.authorizeRequests().antMatchers("/url").permitAll()也能实现如下效果，区别在于webSecurity.ignoring().antMatchers("/url")相当于直接绕过security所有filter。
//    @Override
//    public void configure(WebSecurity webSecurity) throws Exception {
//        webSecurity.ignoring().antMatchers("/static/css/**");
//    }

}
