package io.afu.aicenterapi.components;




import io.afu.aicenterapi.exceptions.BaseException;
import io.afu.aicenterapi.pojo.business.dto.wx.WxVerifyReq;
import io.afu.aicenterapi.pojo.business.vo.wx.WxCode2SessionResp;
import io.afu.aicenterapi.properties.WechatProperties;
import io.afu.aicenterapi.utils.JsonUtil;
import io.afu.aicenterapi.utils.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */

public class WechatComponent {

    WechatProperties properties;

    private RestTemplate restTemplate = new RestTemplate();

    public WechatComponent() {

    }

    public WechatComponent(WechatProperties wechatProperties) {
        this.properties = wechatProperties;
    }



    /**
     * 检查是否已经配置了
     * @param ifSmall 检查这个是否是微信小程序的
     * @throws BaseException 抛错检查失败错误
     */
    public void checkConfig(boolean ifSmall) throws BaseException {
        if (ifSmall) {
            if (StringUtils.isEmpty(properties.getSmallAppId())||StringUtils.isEmpty(properties.getSmallAppSecret())){
                throw new BaseException("微信小程序的appId或者AppSecret没有配置，请核对配置名称或者是否忘记配置");
            }
        }else {
            if (StringUtils.isEmpty(properties.getGzhAppId())||StringUtils.isEmpty(properties.getGzhAppSecret())||StringUtils.isEmpty(properties.getGzhToken())||StringUtils.isEmpty(properties.getGzhEncodingAesKey())){
                throw new BaseException("微信公众号的appId或者AppSecret没有配置，请核对配置名称或者是否忘记配置");
            }
        }
    }

    /**
     * 通过Code 来获取Token和Session等
     * @param code 用户前端微信公众号或者小程序获取到的code
     * @param ifSmall 是否是微信小程序获得的
     * @return WxCode2SessionResp 微信小程序的Code换Session
     * @throws BaseException 错误
     */
    public WxCode2SessionResp getSessionByCode(String code, boolean ifSmall) throws BaseException {
        checkConfig(ifSmall);
        String appId = properties.getGzhAppId();
        String appSecret = properties.getGzhAppSecret();
        if (ifSmall){
            appSecret = properties.getSmallAppSecret();
            appId = properties.getSmallAppId();
        }
        String fullUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        String result = restTemplate.getForObject(fullUrl,String.class,httpEntity);
        return JsonUtil.parse(result,WxCode2SessionResp.class);
    }


    /**
     * 微信公众号验证URL使用
     * @param verifyReq 验证请求实体类
     * @return 正常返回请求中echostr，错误则返回false
     */
    public String verify(WxVerifyReq verifyReq) throws BaseException {
        checkConfig(false);
        String[] arr = {
                verifyReq.getTimestamp(),
                verifyReq.getNonce(),
                properties.getGzhToken()
        };
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        //sha1加密
        String temp = getSha1(content.toString());
        if (temp.equals(verifyReq.getSignature())) {
            return verifyReq.getEchostr();
        } else {
            return "false";
        }
    }

    /**
     * String sha1的加密
     * @param str 需要加密的String
     * @return 加密后的String
     */
    private String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest mdTemp;
        try {
            mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b0 = md[i];
                buf[k++] = hexDigits[b0 >>> 4 & 0xf];
                buf[k++] = hexDigits[b0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

}
