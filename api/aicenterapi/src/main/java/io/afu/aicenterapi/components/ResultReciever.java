package io.afu.aicenterapi.components;

import io.afu.aicenterapi.config.sys.RabbitMQConfig;
import io.afu.aicenterapi.pojo.business.dto.TaskResultDTO;
import io.afu.aicenterapi.service.AitaskService;
import io.afu.aicenterapi.utils.AESEncrypter;
import io.afu.aicenterapi.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResultReciever {

    @Autowired
    AESEncrypter aesEncrypter;

    @Autowired
    AitaskService aitaskService;



    @RabbitListener(queues = {RabbitMQConfig.RESULT_QUEUE})
    public void recieveResult(String result) {
        log.info("recieve result: {}", result);
        result = result.replace("\"", "");
        try {
            String decrypted = aesEncrypter.decrypt(result);
            log.info("decrypted: {}", decrypted);
            TaskResultDTO taskResultDTO = JsonUtil.parse(decrypted, TaskResultDTO.class);
            if (taskResultDTO != null) {
                aitaskService.updateTextById(taskResultDTO.getTask().getId(), taskResultDTO.getData().getText());
            }
        }catch (Exception e) {
            log.error("error: {}", e.getMessage());
        }

    }




}
