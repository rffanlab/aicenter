package io.afu.aicenterapi.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author rffanlab
 * @since 2024-03-28
 */
public class Aitask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 目前仅有：TEXT_REG
     */
    private String taskType;

    /**
     * AUDIO 音频, VIDEO 视频 , DOUYIN 抖音 , KUAISHOU 快手
     */
    private String dataType;

    /**
     * 数据URL地址用来给远程业务下载的
     */
    private String url;

    /**
     * 解析后的数据文本
     */
    private String dataText;

    /**
     * 解析后的数据URL打包地址
     */
    private String dataUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDataText() {
        return dataText;
    }

    public void setDataText(String dataText) {
        this.dataText = dataText;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    @Override
    public String toString() {
        return "Aitask{" +
            "id = " + id +
            ", taskType = " + taskType +
            ", dataType = " + dataType +
            ", url = " + url +
            ", dataText = " + dataText +
            ", dataUrl = " + dataUrl +
        "}";
    }
}
