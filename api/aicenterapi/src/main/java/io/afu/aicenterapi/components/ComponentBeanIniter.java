package io.afu.aicenterapi.components;

import io.afu.aitaskservice.utils.AESEncrypter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ComponentBeanIniter {


    @Value("${nativery.encryption.key}")
    private String encryptionKey;

    @Value("${nativery.encryption.iv}")
    private String encryptionIv;

    @Bean
    public AESEncrypter aesEncrypter() {
        return AESEncrypter.getInstance(encryptionKey).setOffset(encryptionIv).useCbc().useBase64Result();
    }







}
