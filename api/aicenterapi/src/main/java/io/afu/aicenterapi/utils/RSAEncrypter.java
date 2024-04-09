package io.afu.aicenterapi.utils;



import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class RSAEncrypter {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    /**
     * 生成公钥和私钥
     * @param keySize key大小，最小是1024-2048
     * @return 公钥私钥的秘钥对map
     */
    public static Map<String,String> genKeys(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr =Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        Map<String,String> keyMap = new HashMap<>();
        keyMap.put("publicKey",publicKeyStr);
        keyMap.put("privateKey",privateKeyStr);
        return keyMap;
    }

    /**
     * 从公钥字符串获取公钥
     * @param publicKey 公钥
     * @return 返回公钥对象
     * @throws Exception 抛错
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        return  (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 从私钥字符串获取私钥
     * @param privateKey 私钥
     * @return 返回私钥对象
     * @throws Exception 抛错
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        return  (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 公钥加密
     * @param data 数据
     * @param publicKey 公钥key
     * @return 加密值
     * @throws Exception 抛错
     */
    public static String encryptByPublicKey(String data,RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
    }

    /**
     * 通过私钥解密
     * @param data 密文
     * @param privateKey 私钥
     * @return 返回明文
     * @throws Exception 抛错
     */
    public static String decryptByPrivateKey(String data,RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), privateKey.getModulus().bitLength()), CHARSET);
    }

    /**
     * 通过私钥加密
     * @param data 需要加密的值
     * @param privateKey 私钥
     * @return 返回加密后的值
     * @throws Exception 抛错
     */
    public static String encryptByPrivateKey(String data,RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
    }

    /**
     * 通过公钥解密
     * @param data 机密数据
     * @param publicKey 公钥
     * @return 解密后的值
     * @throws Exception 抛错
     */
    public static String decryptByPublicKey(String data,RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data), publicKey.getModulus().bitLength()), CHARSET);
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        try {
            out.close();
        }catch (IOException ignore) {
        }
        return resultDatas;
    }

    public static void main(String[] args) throws Exception {

    }



}
