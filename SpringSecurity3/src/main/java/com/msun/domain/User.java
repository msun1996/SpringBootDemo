package com.msun.domain;

import lombok.Data;

import java.util.List;

@Data
public class User {
    public int id;
    public String username;
    public String password;
    public List<Role> sysRoles;
}
