package com.msun.dao;

import com.msun.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    // 创建用户
    void save(User user);
    // 删除用户
    void delete(Integer id);
    // 通过ID查询用户
    User getById(Integer id);
    // 根据姓名查询用户
    User getByUserName(String username);
}
