package io.afu.aicenterapi.service.impl;

import io.afu.aicenterapi.config.sys.RabbitMQConfig;
import io.afu.aicenterapi.dao.entity.Aitask;
import io.afu.aicenterapi.exceptions.BaseException;
import io.afu.aicenterapi.pojo.business.dto.TaskResultDTO;
import io.afu.aicenterapi.pojo.business.vo.ShortVVO;
import io.afu.aicenterapi.service.AitaskService;
import io.afu.aicenterapi.service.SttService;
import io.afu.aicenterapi.service.VideoService;
import io.afu.aicenterapi.utils.AESEncrypter;
import io.afu.aicenterapi.utils.JsonUtil;
import io.afu.aicenterapi.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SttServiceImpl implements SttService {


    @Autowired
    AitaskService aitaskService;

    @Autowired
    VideoService videoService;


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AESEncrypter aesEncrypter;


    @Override
    public Integer stt(String url) {
        ShortVVO shortVVO= videoService.parseShortV(url);
        if (shortVVO == null) {
            throw new BaseException("解析视频失败");
        }
        String dataType = getDataTypeFromUrl(url);
        if (StringUtils.isNotEmpty(shortVVO.getAudioUrl()) && shortVVO.getAudioUrl().startsWith("http")) {
            dataType = "AUDIO";
        }
        Aitask queried = aitaskService.findByTaskTypeDataTypeAndDataUrlThatDataTextNotNull(
                "TEXT_REG",
                dataType,
                shortVVO.getFullUrl()
        );
        if (queried != null) {
            return queried.getId();
        }
        // 保存到任务数据库
        Aitask aitask =aitaskService.addAitask(
                "TEXT_REG",
                dataType,
                shortVVO.getFullUrl(),
                shortVVO.getAudioUrl()
        );
        // 发送相关的任务
        // 加密任务id
        TaskResultDTO taskResultDTO = new TaskResultDTO();
        TaskResultDTO.Task task = new TaskResultDTO.Task();
        TaskResultDTO.Data data = new TaskResultDTO.Data();
        task.setId(aitask.getId());
        task.setType("TEXT_REG");
        data.setType(aitask.getDataType());
        data.setUrl(aitask.getUrl());
        taskResultDTO.setTask(task);
        taskResultDTO.setData(data);
        String encrypted = aesEncrypter.encrypt(JsonUtil.stringify(taskResultDTO));
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_QUEUE, encrypted);
        return aitask.getId();
    }




    public static String getDataTypeFromUrl(String dataType) {
        if (dataType.contains("douyin")) {
            return "DOUYIN";
        } else if (dataType.contains("kuaishou")) {
            return "KUAISHOU";
        } else if (dataType.contains("acfun")) {
            return "ACFUN";
        }
        return null;
    }


}
