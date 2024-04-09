package io.afu.aicenterapi.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class WebHttpUtil {


    /**
     * 返回请求端的IP地址
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        ip = checkIp(ip) ? ip : (
                checkIp(ip = request.getHeader("Proxy-Client-IP")) ? ip : (
                        checkIp(ip = request.getHeader("WL-Proxy-Client-IP")) ? ip :
                                request.getRemoteAddr()));
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private static boolean checkIp(String ip) {
        return !StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null) {
            return null;
        } else {
            return servletRequestAttributes.getRequest();
        }
    }

    /**
     * 获取get 请求信息
     * @param request 请求
     * @return 结果
     */
    public static String parseParams (HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            stringBuilder.append(name).append("=").append(value).append(";");
        }
        return stringBuilder.toString();
    }

    /**
     * post请求体参数
     * @param request 请求
     * @return 结果
     */
    public static String parseBodyParams (HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 请求头信息
     * @param request 请求
     * @return 结果
     */
    public static String parseRequestHeaders (HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        stringBuilder.append("URL:").append(request.getRequestURI()).append("\n");
        while (headerNames.hasMoreElements()) {
            String name  = headerNames.nextElement();
            String value = request.getHeader(name);
            stringBuilder.append(name).append("=").append(value).append(";").append("\n");
        }
        return stringBuilder.toString();
    }


}
