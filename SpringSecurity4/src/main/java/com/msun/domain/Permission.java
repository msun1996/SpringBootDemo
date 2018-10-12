package com.msun.domain;

import lombok.Data;

@Data
public class Permission {
    public int id;
    public String name;
    public String url;
    public String method;
    public String description;
}
