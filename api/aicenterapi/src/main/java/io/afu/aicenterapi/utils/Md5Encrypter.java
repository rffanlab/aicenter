package io.afu.aicenterapi.utils;




import io.afu.aicenterapi.enums.ConstantEnum;
import io.afu.aicenterapi.exceptions.BaseException;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class Md5Encrypter {


    /**
     * 计算字符串的Md5值
     * @param str 需要md5加密的字符串
     * @return String
     * @throws BaseException 加密时出错抛出的错误
     */
    public static String md5(String str) throws BaseException {

        try{
            StringBuffer hexString = new StringBuffer();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] hash = messageDigest.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        }catch (Exception e){
            throw new BaseException(e);
        }
    }

    public static String md5(byte[] bytes) throws BaseException{
        try{
            StringBuffer hexString = new StringBuffer();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] hash = messageDigest.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        }catch (Exception e){
            throw new BaseException(e);
        }
    }

    /**
     * 计算文件的md5值
     * @param file 需要计算md5值的文件
     * @return String 文件md5值
     * @throws BaseException 计算时出错抛出的错误
     */
    public static String md5(File file) throws BaseException {
        FileInputStream fis = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] arr = new byte[1024 * 8];
            int len = 0;
            while ((len = fis.read(arr)) != -1) {
                md5.update(arr, 0, len);
            }
            return EncryptCommon.byteArrayToHex(md5.digest()).toLowerCase();
        }catch (Exception e){
            throw new BaseException(e);
        }finally {
            if (fis != null){
                try {
                    fis.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过随机值加密密码
     * @param pwd 需要加密的密码
     * @return String
     * @throws BaseException 加密时出错抛出的错误
     */
    public static String encryptPwd(String pwd) throws BaseException {
        try {

            String salt = StringUtils.randStr(4);
            String strToEncrypt = pwd + salt;
            String encryptedStr = md5(strToEncrypt);
            return encryptedStr+salt;
        }catch (Exception e){
            throw new BaseException(e);
        }
    }

    /**
     * 通过字符串和盐值加密密码
     * @param pwd 需要加密的密码
     * @param salt 加入的盐值
     * @return String
     * @throws BaseException 加密出错时抛出的错误
     */
    public static String encryptPwd(String pwd,String salt) throws BaseException {
        try {
            String strToEncrypt = pwd + salt;
            String encryptedStr = md5(strToEncrypt);
            return encryptedStr + salt;
        }catch (Exception e){
            throw new BaseException(e);
        }
    }

    /**
     * 比对密码和加密后的密码是否是相同的密码
     * @param pwd 需要比对的密码
     * @param encryptedPwd 已经加密的密码
     * @return Boolean
     * @throws BaseException 比对加密出错时抛出的错误
     */
    public static boolean comparePwd(String pwd,String encryptedPwd) throws BaseException {
        if (pwd == null||encryptedPwd == null){
            throw new BaseException();
        }
        try {
            String salt = encryptedPwd.substring(encryptedPwd.length()-4);
            System.out.println("salt is "+salt);
            String newEncryptPwd = encryptPwd(pwd,salt);
            System.out.println(newEncryptPwd);
            return newEncryptPwd.equals(encryptedPwd);
        }catch (Exception e){
            throw new BaseException(ConstantEnum.PARAM_ERROR);
        }
    }






}
