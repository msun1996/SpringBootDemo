package com.msun.dao;

import com.msun.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    public SysUser findByUserName(String username);
}
