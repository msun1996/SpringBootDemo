package com.msun.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "my.security")
public class SecurityProperties {
    private SocialProperties social = new SocialProperties();
}
