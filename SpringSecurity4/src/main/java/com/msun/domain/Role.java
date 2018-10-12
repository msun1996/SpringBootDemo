package com.msun.domain;

import lombok.Data;

@Data
public class Role {
    public int id;
    public String name;
    private Integer roleLevel;
    private String description;
    private String menuItems;
}
