package io.afu.aicenterapi.service.impl;

import io.afu.aitaskservice.exceptions.BaseException;
import io.afu.aitaskservice.pojo.business.vo.ShortVVO;
import io.afu.aitaskservice.service.ShortVService;
import org.springframework.stereotype.Service;


@Service(value = "KuaishouService")
public class KuaishouService implements ShortVService {


    @Override
    public ShortVVO parseShortV(String url) {
        throw new BaseException("暂不支持解析快手视频");
    }


    @Override
    public ShortVVO parseShortV(String url, Boolean downloadLocal) {
        return null;
    }
}

