package com.msun.config.security;

import com.msun.dao.PermissionDao;
import com.msun.dao.UserDao;
import com.msun.domain.Permission;
import com.msun.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// 实现从数据库表取出正确的用户，密码，权限存储信息
@Component
public class UrlUserService implements UserDetailsService {

    @Autowired
    public UserDao userDao;
    @Autowired
    public PermissionDao permissionDao;

    //重写loadUserByUsername 方法获得 userDetails 类型用户
    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = userDao.getByUserName(userName);
        if (user != null) {
            List<Permission> permissions = permissionDao.getByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName()!=null) {
                    GrantedAuthority grantedAuthority = new UrlGrantedAuthority(permission.getUrl(),permission.getMethod());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + userName + " do not exist!");
        }
    }

}
