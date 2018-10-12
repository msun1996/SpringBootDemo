package com.msun.service;

import com.msun.domain.primary.User;
import com.msun.domain.second.City;

import java.util.List;

public interface FindAllServer {
    public List<User> FindAllUser();
    public List<City> FindAllCity();
}
