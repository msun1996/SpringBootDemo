package com.msun.sso.sever;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SsoUserDetailsService implements UserDetailsService {

    /**
     * 设置登录用户名和密码（此处可使用数据库提取）
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("admin", new BCryptPasswordEncoder().encode("123456"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
