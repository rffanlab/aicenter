package io.afu.aicenterapi.utils;


import io.afu.aitaskservice.exceptions.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Map;

public class EncryptCommon {

    private static Logger logger = LoggerFactory.getLogger(EncryptCommon.class);


    /**
     * '将byte转换为16进制
     * @param byteArray 需要转换的二进制
     * @return String
     */
    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    public static String formatSign(Map<String,String> params,String seperater) {
        String[] paramsKeys = params.keySet().toArray(new String[0]);
        Arrays.sort(paramsKeys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0;i<paramsKeys.length;i++){
            stringBuilder.append(paramsKeys[i]);
            stringBuilder.append("=");
            if (seperater != null){
                if (i<paramsKeys.length-1){
                    stringBuilder.append(seperater);
                }
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 从身份证中计算年龄
     * @param idcardNumber 身份证号（中国大陆，中国香港和中国台湾不在计算范围内）
     * @return 年龄（无法计算返回0）
     */
    public static Integer calculateAgeFromIdcardNumber(String idcardNumber) {
        Integer age = 0;
        if (idcardNumber == null || idcardNumber.equals("")){
            return age;
        }
        if (idcardNumber.length() != 15 && idcardNumber.length() != 18){
            return age;
        }
        try {
            Calendar cal = Calendar.getInstance();
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH)+1;
            int dayNow = cal.get(Calendar.DATE);

            int year = Integer.valueOf(idcardNumber.substring(6, 10));
            int month = Integer.valueOf(idcardNumber.substring(10,12));
            int day = Integer.valueOf(idcardNumber.substring(12,14));

            if ((month < monthNow) || (month == monthNow && day<= dayNow) ){
                age = yearNow - year;
            }else {
                age = yearNow - year-1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return age;
    }

    /**
     * hmacsha256加密方式进行加密
     * @param message 明文
     * @param secret 秘钥
     * @return 密文字节码
     * @throws Exception 加密出错
     */
    public static byte[] hmacSha256(String message,String secret) throws Exception {
        String algorithm = "HmacSHA256";
        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance(algorithm);
            byte[] keyBytes = secret.getBytes("UTF-8");
            byte[] messageBytes = message.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm));
            // 使用HmacSHA256对二进制数据消息Bytes计算摘要
            byte[] digestBytes = hmacSha256.doFinal(messageBytes);
            // 把摘要后的结果digestBytes使用Base64进行编码
            return digestBytes;
        } catch (NoSuchAlgorithmException e) {
            String msg = MessageFormat.format("不支持此算法: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } catch (UnsupportedEncodingException e) {
            String msg = MessageFormat.format("不支持的字符编码: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } catch (InvalidKeyException e) {
            String msg = MessageFormat.format("无效的密钥规范: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        }
    }

    /**
     * hmacSha256Base64返回
     * @param message 明文
     * @param secret 秘钥
     * @return Base64编码后的密文
     * @throws Exception
     */
    public static String hmacSha256Base64(String message,String secret) throws Exception {
        String algorithm = "HmacSHA256";
        Mac hmacSha256;
        String digestBase64 = null;
        try {
            hmacSha256 = Mac.getInstance(algorithm);
            byte[] keyBytes = secret.getBytes("UTF-8");
            byte[] messageBytes = message.getBytes("UTF-8");
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm));
            // 使用HmacSHA256对二进制数据消息Bytes计算摘要
            byte[] digestBytes = hmacSha256.doFinal(messageBytes);
            // 把摘要后的结果digestBytes使用Base64进行编码
            digestBase64 = new String(Base64.getEncoder().encode(digestBytes),"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            String msg = MessageFormat.format("不支持此算法: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } catch (UnsupportedEncodingException e) {
            String msg = MessageFormat.format("不支持的字符编码: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        } catch (InvalidKeyException e) {
            String msg = MessageFormat.format("无效的密钥规范: {0}", e.getMessage());
            Exception ex = new Exception(msg);
            ex.initCause(e);
            throw ex;
        }
        return digestBase64;
    }

    /**
     * 执行sha256编码
     * @param pendingEncrypt 待编码摘要的字符串
     * @return 编码摘要后的值
     * @throws BaseException 抛错
     */
    public static byte[] sha256(String pendingEncrypt) throws BaseException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(pendingEncrypt.getBytes(StandardCharsets.UTF_8));
            return messageDigest.digest();
        }catch (Exception e) {
            logger.error("取sha256哈希散列出错",e);
            throw new BaseException(e);
        }
    }





}
