package com.msun.properties;

import lombok.Data;

@Data
public class SocialProperties {
    private String filterProcessUrl = "/auth";
    private QQProperties qq= new QQProperties();
}
