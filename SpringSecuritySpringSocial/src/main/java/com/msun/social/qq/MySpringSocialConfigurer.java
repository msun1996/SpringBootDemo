package com.msun.social.qq;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessUrl;

    public MySpringSocialConfigurer(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    // 第三方登录路径重命名（默认/auth）
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        return (T) filter;
    }
}
