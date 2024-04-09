package io.afu.aicenterapi.service;

import io.afu.aicenterapi.pojo.business.vo.ShortVVO;

public interface ShortVService {

    ShortVVO parseShortV(String url);

    ShortVVO parseShortV(String url,Boolean downloadLocal);








}
