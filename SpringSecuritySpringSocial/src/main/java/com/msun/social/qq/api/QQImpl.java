package com.msun.social.qq.api;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;


/**
 * QQ接口用户信息提取实现Api
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    // 获取openId请求，输入参数为accessToken
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    // 获取QQ用户信息,需传入三个参数，accessToken(会在AbstractOAuth2ApiBinding传入拼接),appId(应用号),openId(QQ号)
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        // 调用父类，会将accessToken作为参数挂到请求上
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID,accessToken);
        // 获取带OpenId的JSON字符串,转化为Json对象后获取
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println(jsonObject.getString("openid"));
        this.openId = jsonObject.getString("openid");
    }

    @Override
    public QQUserInfo getUserInfo() {

        String url = String.format(URL_GET_USERINFO, appId, openId);

        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        try {
            return objectMapper.readValue(result, QQUserInfo.class);
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败");
        }
    }
}
