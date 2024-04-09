package io.afu.aicenterapi.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.afu.aicenterapi.exceptions.BaseException;
import io.afu.aicenterapi.pojo.sys.vo.BaseVO;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class CommonUtils {


    private static final String[] CHINESE_NUMBERS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};


    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);


    /**
     * 生成uuid
     * @return String
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 获得一个以年月日时分秒以及uuid产生的前6个字符串的订单id，重复几率非常小
     * @return String
     */
    public static String getOrderNo(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String datetime = simpleDateFormat.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String partOfUUid = uuid.substring(0,6);
        return datetime+partOfUUid.toUpperCase();
    }

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddHHmmssSSS");
        String datetime = simpleDateFormat.format(new Date());
        return datetime;
    }

    public static String getOrderNo(String prefix) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String datetime = simpleDateFormat.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String partOfUUid = uuid.substring(0,6);
        return prefix+datetime+partOfUUid.toUpperCase();
    }

    /**
     * @param theMap 输入的Map参数
     * @return String
     */
    public static String sortMapByAsciiToString(Map<String,String> theMap){
        List<String> toSort = new ArrayList<>(theMap.keySet());
        Collections.sort(toSort);
        String toEdit= "";
        for (String key:toSort){
            if (toEdit.equals("")){
                toEdit = key+"="+theMap.get(key);
            }else {
                toEdit = toEdit + "&"+key+"="+theMap.get(key);
            }
        }
        return toEdit;
    }

    /**
     * @param theMap 输入的Map参数
     * @param sep 分隔符
     * @return String
     */
    public static String sortMapByAsciiToString(Map<String,String> theMap,String sep){
        List<String> toSort = new ArrayList<>(theMap.keySet());
        Collections.sort(toSort);
        String toEdit= "";
        for (String key:toSort){
            if (toEdit.equals("")){
                toEdit = key+"="+theMap.get(key);
            }else {
                toEdit = toEdit +sep+key+"="+theMap.get(key);
            }
        }
        return toEdit;
    }


    /**
     *  将类属性转换为Map
     * @param obj 需要转换属性的实体类对象
     * @return 返回一个Map
     */
    public static Map<String,Object> objectToMap(Object obj){
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将字节码通过base64编码为String
     * @param bytes 字节码
     * @return base64编码后的String
     */
    public static String base64EncodeBytes(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 将base64编码后的String 解码为字节码
     * @param str base64编码后的String
     * @return 字节码
     */
    public static byte[] base64DecodeToBytes(String str) {
        return Base64.getDecoder().decode(str);
    }

    public static byte[] hexToBytes(String hex) {
        hex = hex.length() % 2 != 0 ? "0" + hex : hex;

        byte[] b = new byte[hex.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        return sb.toString();
    }


    public static void flushOut(HttpServletResponse response, BaseException e) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(BaseVO.fail(e));
        logger.info("数据为："+result);
        OutputStream os = response.getOutputStream();
        os.write(result.getBytes(StandardCharsets.UTF_8.toString()));
        os.flush();
    }


    public static String runCmd(String[] cmd) {
        String resultLine = "";
        try {
            // 使用ProcessBuilder执行命令
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(cmd);
            Process process = builder.start();

            // 读取命令的输出
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                resultLine+=line;
            }
            // 等待命令执行完成
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
            } else {
                // 错误处理
                System.out.println("Error");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return resultLine;
    }

}
