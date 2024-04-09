package io.afu.aicenterapi.service.impl;

import io.afu.aicenterapi.components.WechatComponent;
import io.afu.aicenterapi.exceptions.BaseException;
import io.afu.aicenterapi.pojo.business.dto.wx.WxVerifyReq;
import io.afu.aicenterapi.properties.WechatProperties;
import io.afu.aicenterapi.service.WechatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
@Service
public class WechatServiceImpl implements WechatService {


    @Autowired
    WechatProperties wechatProperties;

    @Override
    public String verify(WxVerifyReq req) throws BaseException {
        WechatComponent wechatComponent = new WechatComponent(wechatProperties);
        return wechatComponent.verify(req);
    }


}
