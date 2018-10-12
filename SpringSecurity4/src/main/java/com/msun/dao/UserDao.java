package com.msun.dao;

import com.msun.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User getById(Integer id);
    User getByUserName(String username);
}
