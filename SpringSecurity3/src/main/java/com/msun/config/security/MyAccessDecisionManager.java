package com.msun.config.security;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {

    //decide 方法是判定是否拥有权限的决策方法
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String url, method;
        // 此处用于允许所有用户访问url路径
        if ("anonymousUser".equals(authentication.getPrincipal())
                || matchers("/js/**", request)
                || matchers("/css/**", request)
                || matchers("/fonts/**", request)
                || matchers("/", request)
                || matchers("/login", request)
        ) {
            return;
        } else {
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (ga instanceof MyGrantedAuthority) {
                    MyGrantedAuthority urlGrantedAuthority = (MyGrantedAuthority) ga;
                    url = urlGrantedAuthority.getPermissionUrl();
                    method = urlGrantedAuthority.getMethod();
                    if (matchers(url, request)) {
                        if (method.equals(request.getMethod()) || "ALL".equals(method)) {
                            return;
                        }
                    }
                }
            }
        }

        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private boolean matchers(String url, HttpServletRequest request) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
        if (matcher.matches(request)) {
            return true;
        }
        return false;
    }
}