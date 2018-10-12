package com.msun.service.impl;

import com.msun.dao.primary.UserDao;
import com.msun.dao.second.CityDao;
import com.msun.domain.primary.User;
import com.msun.domain.second.City;
import com.msun.service.FindAllServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllServerImpl implements FindAllServer {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Override
    public List<User> FindAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public List<City> FindAllCity() {
        return cityDao.findAllCity();
    }
}
