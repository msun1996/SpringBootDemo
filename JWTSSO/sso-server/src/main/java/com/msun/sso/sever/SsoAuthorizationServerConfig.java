package com.msun.sso.sever;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置可发放令牌应用
        clients.inMemory()
                .withClient("mSun1")
                // secret密码配置从 Spring Security 5.0开始必须以 {加密方式}+加密后的密码 这种格式填写
                /*
                *   当前版本5新增支持加密方式：
                *   bcrypt - BCryptPasswordEncoder (Also used for encoding)
                *   ldap - LdapShaPasswordEncoder
                *   MD4 - Md4PasswordEncoder
                *   MD5 - new MessageDigestPasswordEncoder("MD5")
                *   noop - NoOpPasswordEncoder
                *   pbkdf2 - Pbkdf2PasswordEncoder
                *   scrypt - SCryptPasswordEncoder
                *   SHA-1 - new MessageDigestPasswordEncoder("SHA-1")
                *   SHA-256 - new MessageDigestPasswordEncoder("SHA-256")
                *   sha256 - StandardPasswordEncoder
                */
                .secret("{noop}mSunSecret1")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .redirectUris("http://127.0.0.1:8080/client1/login")
                .and()
                .withClient("mSun2")
                .secret("{noop}mSunSecret2")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("all")
                .redirectUris("http://127.0.0.1:8081/client2/login");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //经过验证可来服务器区可解密的tokenKey
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("mSun");
        return converter;
    }
}
