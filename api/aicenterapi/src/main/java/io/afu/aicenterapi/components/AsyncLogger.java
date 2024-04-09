package io.afu.aicenterapi.components;

import ch.qos.logback.core.spi.LogbackLock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.afu.aicenterapi.exceptions.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ResponseFacade;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AsyncLogger {


    private ObjectMapper objectMapper = setupObjectMapper();

    private ObjectMapper setupObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }



    private String parseParam(Object o) throws JsonProcessingException {
        if (o instanceof MultipartFile) {
            Map<String, String> map = new HashMap<>();
            MultipartFile file = (MultipartFile) o;
            map.put("filename", file.getOriginalFilename());
            return objectMapper.writeValueAsString(map);
        } else if (o instanceof BaseException) {
            BaseException exception = (BaseException) o;
            StringBuilder sb = new StringBuilder("[ERROR][").append(exception.getCode()).append("]").append(exception.getMessage());
            return sb.toString();
        } else if (o instanceof Exception) {
            Exception exception = (Exception) o;
            StringBuilder sb = new StringBuilder("[ERROR]").append(exception.getMessage());
            return sb.toString();
        } else if (o instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) o;
            StringBuilder sb = new StringBuilder("[REQUEST_URL]").append(request.getRequestURI());
            return sb.toString();
        } else if (o instanceof LogbackLock) {
            return new StringBuilder("[").append(LogbackLock.class).append("]").toString();
        } else if (o instanceof ResponseFacade) {
            return "[]";
        } else {
            return objectMapper.writeValueAsString(o);
        }
    }

    @Async
    public void info(final String content) {
        log.info(content == null ? "" : content);
    }

    @Async
    public void debug(final String content) {
        log.debug(content == null ? "" : content);
    }






}
