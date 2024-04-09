package io.afu.aicenterapi.service.impl;

import io.afu.aicenterapi.exceptions.BaseException;
import io.afu.aicenterapi.pojo.business.vo.ShortVVO;
import io.afu.aicenterapi.service.ShortVService;
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

