package io.afu.aicenterapi.service;

import io.afu.aitaskservice.pojo.business.dto.SharedUrlDTO;
import io.afu.aitaskservice.pojo.business.vo.ShortVVO;

public interface VideoService {


    ShortVVO parseShortV(SharedUrlDTO dto);

    ShortVVO parseShortV(SharedUrlDTO dto,Boolean downloadLocal);

    ShortVVO parseShortV(String url);


}
