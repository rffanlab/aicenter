package io.afu.aicenterapi.components;

import ch.qos.logback.core.spi.LogbackLock;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.afu.aitaskservice.exceptions.BaseException;
import io.afu.aitaskservice.pojo.sys.vo.BaseVO;
import io.afu.aitaskservice.utils.JsonUtil;
import io.afu.aitaskservice.utils.WebHttpUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * API 业务日志打印，打印级别为 DEBUG
 * 只有在设置级别为DEBUG 时才会打印
 */
@Component
@Aspect
@Slf4j
@ConditionalOnProperty("nativery.log.auto")
@Order(-1)
public class ApiLog {

    String[] headerNames;

    /**
     * 切入点为所有的请求方法
     */
    @Pointcut(value = "execution(* io.afu.aitaskservice.controller..*.*(..)) ")
    public void cutPostMapping() {
    }

    @Autowired
    AsyncLogger asyncLogger;

    private ObjectMapper objectMapper = setupObjectMapper();

    private ObjectMapper setupObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper;
    }


    @Around("cutPostMapping()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        Method method = getMethod(point);
        String categoryName = null;
        boolean logHeaderEnabled = true;
        boolean logTimeEnabled = false;
        boolean disableLog = false;
        boolean disableResponse = false;



        if (disableLog) {
            return point.proceed();
        }

        StringBuilder logContent = new StringBuilder();
        //追加描述
        String category = getCategory(method, categoryName);
        if (StringUtils.isNotEmpty(category)) {
            logContent.append("[描述]").append(category).append("\n");
        }


        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null) {
            String httpMethod = httpServletRequest.getMethod().toUpperCase();
            //追加请求方式+请求地址
            logContent.append("[").append(WebHttpUtil.getIP(httpServletRequest)).append("]")
                    .append("[").append(httpMethod).append("]")
                    .append(getUrl(httpServletRequest)).append("\n");

            //追加请求头
            if (logHeaderEnabled) {
                Map<String, String> headers = getHeaders(httpServletRequest);
                logContent.append("[headers]").append(JsonUtil.stringify(headers)).append("\n");
            }
        }

        //追加入参
        logContent.append("[入参]").append(getRequestParams(point.getArgs()));

        //打印接口请求数据
        asyncLogger.debug(logContent.toString());

        if (disableResponse) {
            return point.proceed();
        }

        logContent = new StringBuilder();

        Object result;
        if (logTimeEnabled) {
            long startTime = System.currentTimeMillis();
            result = point.proceed();
            long endTime = System.currentTimeMillis();
            logContent.append("[耗时]" + (endTime - startTime) + "ms\n");
        } else {
            result = point.proceed();
        }

        if (result instanceof BaseVO<?> && ((BaseVO<?>)result).hasSuccess()) {
            BaseVO<?> baseResp = (BaseVO<?>)result;
            logContent.append("[成功出参]" + (baseResp.getData() == null ? "无" : JsonUtil.stringify(baseResp.getData())));
        } else {
            logContent.append("[出参]" + (result == null ? "" : JsonUtil.stringify(result)));
        }


        //打印接口返回数据
        asyncLogger.debug(logContent.toString());
        return result;
    }

    /**
     * 获取方法
     */
    private Method getMethod(ProceedingJoinPoint point) throws Throwable {
        Signature sig = point.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature msig = (MethodSignature) sig;
        Object target = point.getTarget();
        return target.getClass().getDeclaredMethod(msig.getName(), msig.getParameterTypes());
    }

    /**
     * 获取类型
     */
    private String getCategory(Method method, String categoryName) {
        String category = categoryName;
        // 从swagger上获取类型


        return category;
    }

    /**
     * 获取http请求体
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    /**
     * 获取url
     */
    private String getUrl( HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
        String queryString = request.getQueryString();
        if (StringUtils.isNotEmpty(queryString)) {
            url += "?" + queryString;
        }
        return url;
    }

    /**
     * 获取头部数据
     */

    private Map<String, String> getHeaders( HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        if (headerNames != null && headerNames.length > 0) {
            if (Objects.equals(headerNames[0], "all")) {
                Enumeration<String> enumeration = request.getHeaderNames();
                while (enumeration.hasMoreElements()) {
                    String name = enumeration.nextElement();
                    headers.put(name, request.getHeader(name));
                }

            } else {
                for (String name : headerNames) {
                    headers.put(name, request.getHeader(name));
                }
            }
        }
        return headers;
    }

    /**
     * 获取请求入参
     */

    private String getRequestParams( Object[] params) {
        List<Object> list = null;
        if (params != null && params.length > 0) {
            try {
                list = Stream.of(params).filter(Objects::nonNull).map(o -> {
                    if (o instanceof MultipartFile) {
                        Map<String, String> map = new HashMap<>();
                        MultipartFile file = (MultipartFile) o;
                        map.put("type", "MultipartFile");
                        map.put("filename", file.getOriginalFilename());
                        return map;
                    } else if (o instanceof BaseException) {
                        Map<String, String> map = new HashMap<>();
                        BaseException baseException = (BaseException) o;
                        map.put("type", "BaseException");
                        map.put("code", String.valueOf(baseException.getCode()));
                        map.put("message", baseException.getMessage());
                        return map;
                    } else if (o instanceof Exception) {
                        Map<String, String> map = new HashMap<>();
                        Exception exception = (Exception) o;
                        map.put("type", "Exception");
                        map.put("message", exception.getMessage());
                        return map;
                    } else if (o instanceof ServletRequest) {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", "ServletRequest");
                        return map;
                    } else if (o instanceof ResponseFacade) {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", "ResponseFacade");
                        return map;
                    } else if (o instanceof LogbackLock) {
                        Map<String, String> map = new HashMap<>();
                        map.put("type", "LogbackLock");
                        return map;

                    } else {
                        return o;
                    }

                }).collect(Collectors.toList());

            } catch (Exception e) {
                return "解析失败, " + e.getMessage();
            }
        }

        return list == null ? "" : JsonUtil.stringify(list);
    }




}
