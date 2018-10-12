package com.msun.properties;

import lombok.Data;

@Data
public class QQProperties {
    // appId和密匙
    private String appId;
    private String appSecret;
    private String providerId = "qq";
}
