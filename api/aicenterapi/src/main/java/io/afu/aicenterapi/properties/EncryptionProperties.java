package io.afu.aicenterapi.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nativery.encryption")
@Data
@ConfigurationPropertiesScan
public class EncryptionProperties {


    /**
     * 是否启用加密
     */
    private boolean enabled;

    /**
     * 加密key
     */
    private String key;

    /**
     * 加密iv
     */
    private String iv;



}
