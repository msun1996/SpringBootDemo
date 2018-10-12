package com.msun.config;


import com.msun.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Security主配置文件
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringSocialConfigurer mySocialConfigurer;

    @Bean
    UserDetailsService myUserDetailsService() {
        return new MyUserDetailsService();
    }

    /**
     * 登录验证，判定自定义验证方式
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(myUserDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .apply(mySocialConfigurer)
                .and()
                .authorizeRequests()
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
