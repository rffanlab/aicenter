package io.afu.aicenterapi.service;


import io.afu.aicenterapi.exceptions.BaseException;
import io.afu.aicenterapi.pojo.business.dto.wx.WxVerifyReq;

public interface WechatService {


    String verify(WxVerifyReq req) throws BaseException;




}
