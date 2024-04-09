package io.afu.aicenterapi.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {


    public static ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static <T> T parse(String jsonString, Class<T> classType) {
        try {
            if (StringUtils.isEmpty(jsonString)) {
                return null;
            }
            return objectMapper.readValue(jsonString, classType);
        } catch (Exception e) {
            log.error("json解析报错", e);
            return null;
        }
    }

    public static <T> T parse(String jsonString, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (Exception e) {
            log.error("json解析报错", e);
            return null;
        }
    }

    public static <T> T parse(Object object, Class<T> classType) {
        try {
            return objectMapper.convertValue(object, classType);
        } catch (Exception e) {
            log.error("json解析报错", e);
            return null;
        }
    }

    public static <T> T parse(Object object, TypeReference<T> typeReference) {
        try {
            return objectMapper.convertValue(object, typeReference);
        } catch (Exception e) {
            log.error("json解析报错", e);
            return null;
        }
    }

    public static String stringify(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("json解析报错:{}", e.getMessage(), e);
            return null;
        }
    }
}
