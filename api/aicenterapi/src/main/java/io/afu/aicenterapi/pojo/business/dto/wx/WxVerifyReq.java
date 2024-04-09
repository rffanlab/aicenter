package io.afu.aicenterapi.pojo.business.dto.wx;

import lombok.Data;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
@Data
public class WxVerifyReq {

    private String signature;

    private String timestamp;

    private String nonce;

    private String echostr;

}
