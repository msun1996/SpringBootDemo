package com.msun.service;

import com.msun.dao.UserDao;
import com.msun.domain.SysRole;
import com.msun.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = userDao.findByUserName(username);
        if(user==null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        // 用于添加用户权限
        for(SysRole role:user.getSysRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            System.out.println(role.getName());
            System.out.println(authorities);
        }
        // 测试时，因为数据库存储的密码为手动加入，未加密。所以下面return的密码进行加密处理，正式使用去掉。
        return new org.springframework.security.core.userdetails.User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), authorities);
    }
}
