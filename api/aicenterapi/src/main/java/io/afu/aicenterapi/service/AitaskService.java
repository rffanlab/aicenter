package io.afu.aicenterapi.service;

import io.afu.aitaskservice.dao.entity.Aitask;

public interface AitaskService {



    Aitask addAitask(String taskType,String dataType,String url);

    Aitask addAitask(String taskType,String dataType,String dataUrl,String url);


    Aitask findById(Integer id);

    void updateTextById(Integer id,String text);

    Aitask findByTaskTypeDataTypeAndDataUrlThatDataTextNotNull(String taskType, String dataType, String dataUrl);

    void updateDataUrlById(Integer id,String dataUrl);

}
