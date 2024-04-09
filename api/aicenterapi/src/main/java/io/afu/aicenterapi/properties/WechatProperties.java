package io.afu.aicenterapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "wechat")
@Component
public class WechatProperties {

    /**
     * 公众号APPID
     */
    private String gzhAppId;

    /**
     * 公众号APPSecret
     */
    private String gzhAppSecret;

    /**
     * 公众号Token
     */
    private String gzhToken;

    /**
     * 公众号AesKey
     */
    private String gzhEncodingAesKey;

    private String smallAppId;

    private String smallAppSecret;


    public String getGzhToken() {
        return gzhToken;
    }

    public void setGzhToken(String gzhToken) {
        this.gzhToken = gzhToken;
    }

    public String getGzhEncodingAesKey() {
        return gzhEncodingAesKey;
    }

    public void setGzhEncodingAesKey(String gzhEncodingAesKey) {
        this.gzhEncodingAesKey = gzhEncodingAesKey;
    }

    public String getGzhAppId() {
        return gzhAppId;
    }

    public void setGzhAppId(String gzhAppId) {
        this.gzhAppId = gzhAppId;
    }

    public String getGzhAppSecret() {
        return gzhAppSecret;
    }

    public void setGzhAppSecret(String gzhAppSecret) {
        this.gzhAppSecret = gzhAppSecret;
    }

    public String getSmallAppId() {
        return smallAppId;
    }

    public void setSmallAppId(String smallAppId) {
        this.smallAppId = smallAppId;
    }

    public String getSmallAppSecret() {
        return smallAppSecret;
    }

    public void setSmallAppSecret(String smallAppSecret) {
        this.smallAppSecret = smallAppSecret;
    }
}
