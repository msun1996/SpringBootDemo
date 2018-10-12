package com.msun.dao;

import com.msun.domain.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionDao {
    public List<SysPermission> findAll();
    public List<SysPermission> findByUserId(int userId);
}
