package com.msun.sso.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class SsoClient1Application {

    /**
     * 获取登录用户信息
     */
    @RequestMapping("/user")
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClient1Application.class,args);
    }
}
