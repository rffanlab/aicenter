package io.afu.aicenterapi.filters;


import io.afu.aicenterapi.properties.NativeryProperties;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@ConditionalOnProperty(prefix = "nativerymp.encryption", name = "enabled", havingValue = "true")
@WebFilter(filterName = "encryptionFilter", urlPatterns = "/")
@Order(-9999) // 优先级设置为最高
@Slf4j
public class EncryptionFilter implements Filter {

    NativeryProperties nativeryProperties;

    @Autowired
    public void setNativeryProperties(NativeryProperties nativeryProperties) {
        this.nativeryProperties = nativeryProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("开始处理请求的加密");
        // 只有在post json的情况下才会将数据进行AES 加解密处理

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String method = request.getMethod();
        if (method.equals("POST") ){
            if (nativeryProperties.getEncryption().check()) {
                // 检查通过才进行加解密操作，否则将不会进行任何操作，以防止因为配置错误导致的数据丢失
                // 开始获取请求体数据
                String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
                log.info("请求体数据: {}", body);
            }
        }

    }
}
