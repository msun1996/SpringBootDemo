package com.msun.service;

import com.msun.dao.UserDao;
import com.msun.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserDao userDao;

    public User getById(Integer id) {
        User user = userDao.getById(id);
        return user;
    }

}
