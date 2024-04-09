package io.afu.aicenterapi.controller;


import io.afu.aicenterapi.pojo.business.dto.SharedUrlDTO;
import io.afu.aicenterapi.pojo.sys.vo.BaseVO;
import io.afu.aicenterapi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shortv")
public class ShortVController {


    @Autowired
    VideoService videoService;

    @PostMapping("parseurl")
    public BaseVO parseUrl(
            @RequestBody SharedUrlDTO dto
            ) {
        return BaseVO.success(videoService.parseShortV(dto));
    }





















}
