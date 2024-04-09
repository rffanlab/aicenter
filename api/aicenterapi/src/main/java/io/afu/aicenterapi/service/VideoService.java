package io.afu.aicenterapi.service;

import io.afu.aicenterapi.pojo.business.dto.SharedUrlDTO;
import io.afu.aicenterapi.pojo.business.vo.ShortVVO;

public interface VideoService {


    ShortVVO parseShortV(SharedUrlDTO dto);

    ShortVVO parseShortV(SharedUrlDTO dto,Boolean downloadLocal);

    ShortVVO parseShortV(String url);


}
