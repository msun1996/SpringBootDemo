package com.msun.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class User {
    public int id;
    public String username;
    public String password;
}
