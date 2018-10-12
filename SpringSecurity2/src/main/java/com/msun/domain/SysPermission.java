package com.msun.domain;

import lombok.Data;

@Data
public class SysPermission {
    public int id;
    public String name;
    public String description;
    public String url;
    public int pid;
}
