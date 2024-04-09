package io.afu.aicenterapi.filters;



import io.afu.aicenterapi.pojo.sys.dto.CommonBody;
import io.afu.aicenterapi.properties.NativeryProperties;
import io.afu.aicenterapi.utils.AESEncrypter;
import io.afu.aicenterapi.utils.JsonUtil;
import io.afu.aicenterapi.utils.StringUtils;
import io.afu.aicenterapi.utils.WebHttpUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@WebFilter(filterName = "httpServletRequestFilter", urlPatterns = "/")
@Slf4j
public class HttpServletRequestFilter implements Filter {

    @Autowired
    NativeryProperties nativeryProperties;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            String method = ((HttpServletRequest) servletRequest).getMethod();
            if ("POST".equals(method) ) {
                // 在POST的情况下，通过判断是否有文件上传，来进行不同的处理
                String contentType = servletRequest.getContentType();
                if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
                    // 这里无法确定multipart的文件是否是上传的文件，因此这里就不做请求体的重新包装。
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest,nativeryProperties);
        }
        //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中
        if (null == requestWrapper) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    public static class RequestWrapper extends HttpServletRequestWrapper {

        /**
         * 请求体
         */
        private final String body;

        private NativeryProperties nativeryProperties;


        public RequestWrapper(HttpServletRequest request,NativeryProperties nativeryProperties) {
            super(request);
            String body1 = getBody(request);
            this.nativeryProperties = nativeryProperties;
            // 将body数据存储起来
            if (nativeryProperties.getEncryption().isEnabled()) {

                try {
                    CommonBody commonBody = JsonUtil.parse(body1, CommonBody.class);
                    if (commonBody == null) {

                    }else {
                        String decrypted = AESEncrypter.getInstance(nativeryProperties.getEncryption().getKey())
                                .setOffset(nativeryProperties.getEncryption().getIv())
                                .useCbc().useBase64Result()
                                .decrypt(commonBody.getEncryptedData());
                        ;
                        if (StringUtils.isNotEmpty(decrypted)) {
                            body1 = decrypted;
                        }
                    }
                    log.info("请求体数据(解密): {}", body1);
                } catch (Exception e) {
                    log.error("请求体数据解析失败", e);
                }
            }
            this.body = body1;
        }

        /**
         * 获取请求体
         *
         * @param request 请求
         * @return 请求体
         */
        private String getBody(HttpServletRequest request) {
            return WebHttpUtil.parseBodyParams(request);
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            // 创建字节数组输入流
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() {
                    return byteArrayInputStream.read();
                }
            };
        }
    }
}
