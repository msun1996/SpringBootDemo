package com.msun.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }
    @Autowired
    private UrlFilterSecurityInterceptor urlFilterSecurityInterceptor;

    @Autowired
    private UrlUserService urlUserService;
    @Autowired
    SessionRegistry sessionRegistry;

    // 登录验证，绑定自定义验证
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(urlUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                // 并发控制（一个用户最大session数量）
                .sessionManagement()
                .maximumSessions(1)
                // 并发时使新用户拒绝登入而不是踢掉原用户
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry)
                .and()
                .and()
                .logout() // 注销登录

                .and()
                .httpBasic();
        httpSecurity.addFilterBefore(urlFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 关闭CSRF验证
                .csrf().disable();
    }
}
