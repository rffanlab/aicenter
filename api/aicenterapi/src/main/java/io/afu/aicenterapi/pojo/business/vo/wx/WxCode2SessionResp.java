package io.afu.aicenterapi.pojo.business.vo.wx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxCode2SessionResp extends WxCommonResp {

    private String openid;

    @JsonProperty("session_key")
    private String sessionKey;

    private String unionid;


}
