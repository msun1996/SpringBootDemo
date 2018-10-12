package com.msun.dao;

import com.msun.domain.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionDao {
    List<Permission> getByUserId(int userId);
}
