package com.msun.domain;

import lombok.Data;

import java.util.List;

@Data
public class SysUser {
    private int id;
    private String username;
    private String password;
    private List<SysRole> sysRoles;
}
