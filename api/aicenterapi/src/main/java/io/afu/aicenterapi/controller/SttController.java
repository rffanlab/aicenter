package io.afu.aicenterapi.controller;


import io.afu.aicenterapi.dao.entity.Aitask;
import io.afu.aicenterapi.pojo.business.dto.QuerySttResultDTO;
import io.afu.aicenterapi.pojo.business.dto.SharedUrlDTO;
import io.afu.aicenterapi.pojo.sys.vo.BaseVO;
import io.afu.aicenterapi.service.AitaskService;
import io.afu.aicenterapi.service.SttService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/stt")
public class SttController {


    @Autowired
    AitaskService aitaskService;

    @Autowired
    SttService sttService;


    @PostMapping("/loadText")
    public BaseVO loadTextFromVideo(
            @Param("file") MultipartFile file,
            @RequestBody SharedUrlDTO dto
            ) {
        if (file != null) {
            // 将上传的文件进行解析，并保存到服务器上
        }
        return BaseVO.success(sttService.stt(dto.getSharedurl()));
    }


    @PostMapping("/queryResult")
    public BaseVO queryResult(
            @RequestBody QuerySttResultDTO dto
            ) {
        String text = "";
        Aitask aitask = aitaskService.findById(dto.getId());
        if (aitask != null) {
            text = aitask.getDataText();
        }
        return BaseVO.success(text);
    }












}
