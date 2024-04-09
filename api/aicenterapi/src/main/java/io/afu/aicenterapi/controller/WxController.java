package io.afu.aicenterapi.controller;

import io.afu.aitaskservice.pojo.business.dto.wx.WxVerifyReq;
import io.afu.aitaskservice.service.WechatService;
import io.afu.aitaskservice.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
@Slf4j
public class WxController {


    @Autowired
    WechatService wechatService;

    @RequestMapping("/wxnotify")
    public Object wechatNotify(
            HttpServletRequest request
    ) {
        if ("GET".equals(request.getMethod())) {
            String queryString = request.getQueryString();
            log.info("微信验证接口请求，请求地址为：{}", queryString);
            try {
                WxVerifyReq wxVerifyReq = StringUtils.queryStringToObject(queryString, WxVerifyReq.class);
                return wechatService.verify(wxVerifyReq);
            }catch (Exception e){
                log.error("解析请求参数出错或者是验证出错",e);
            }
        } else if ("POST".equals(request.getMethod())) {

        }
        return "false";
    }








}
