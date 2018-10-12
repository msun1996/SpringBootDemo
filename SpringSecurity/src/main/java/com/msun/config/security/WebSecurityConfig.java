package com.msun.config.security;

import com.msun.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
                // .antMatchers("/","/hello").permitAll()
                // 任意请求，登录后可以访问
                .anyRequest().authenticated()
                .and()
                // 登录页面用户可以任意访问
                .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                // 注销行为任意访问
                .logout().permitAll();
    }
}
