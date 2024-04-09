package io.afu.aicenterapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.afu.aicenterapi.dao.entity.Aitask;
import io.afu.aicenterapi.dao.mapper.AitaskMapper;
import io.afu.aicenterapi.service.AitaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AitaskServiceImpl implements AitaskService {


    @Autowired
    AitaskMapper aitaskMapper;

    @Override
    public Aitask addAitask(String taskType, String dataType, String url) {
        Aitask aitask = new Aitask();
        aitask.setTaskType(taskType);
        aitask.setDataType(dataType);
        aitask.setDataUrl(url);
        aitaskMapper.insert(aitask);
        return aitask;
    }

    @Override
    public Aitask addAitask(String taskType, String dataType, String dataUrl, String url) {
        Aitask aitask = new Aitask();
        aitask.setTaskType(taskType);
        aitask.setDataType(dataType);
        aitask.setDataUrl(dataUrl);
        aitask.setUrl(url);
        aitaskMapper.insert(aitask);
        return aitask;
    }

    @Override
    public Aitask findById(Integer id) {
        return aitaskMapper.selectById(id);
    }

    @Override
    public void updateTextById(Integer id, String text) {
        Aitask aitask =  findById(id);
        if (aitask != null) {
            aitask.setDataText(text);
            aitaskMapper.updateById(aitask);
        }
    }

    @Override
    public Aitask findByTaskTypeDataTypeAndDataUrlThatDataTextNotNull(String taskType, String dataType, String dataUrl) {
        QueryWrapper<Aitask> queryWrapper = new QueryWrapper<>();
        Aitask aitask = new Aitask();
        aitask.setDataType(dataType);
        aitask.setDataUrl(dataUrl);
        aitask.setTaskType(taskType);
        queryWrapper.setEntity(aitask);
        queryWrapper.isNotNull("data_text");
        List<Aitask> aitasks = aitaskMapper.selectList(queryWrapper);
        if (aitasks != null && !aitasks.isEmpty()) {
            return aitasks.get(0);
        }
        return null;
    }

    @Override
    public void updateDataUrlById(Integer id, String dataUrl) {
        Aitask aitask =  findById(id);
        if (aitask != null) {
            aitask.setDataUrl(dataUrl);
            aitaskMapper.updateById(aitask);
        }
    }
}
