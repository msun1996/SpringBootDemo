package com.msun.dao.primary;

import com.msun.domain.primary.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public List<User> findAllUser();
}
