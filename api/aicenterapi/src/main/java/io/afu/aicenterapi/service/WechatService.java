package io.afu.aicenterapi.service;


import io.afu.aitaskservice.exceptions.BaseException;
import io.afu.aitaskservice.pojo.business.dto.wx.WxVerifyReq;

public interface WechatService {


    String verify(WxVerifyReq req) throws BaseException;




}
