package io.afu.aicenterapi.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class AESEncrypter {
    /**
     * 偏移量 需要16位的字符串
     */
    private String OFFSET = "WONDERSWPPRO2021";

    /**
     * 加密的key，必须是16位长度的。 KEY 是必须值。
     */
    private String KEY = "";

    /**
     * 算法默认定义。
     */
    private String ALGORITHMSTR = CBC_PKCS5_PADDING;

    /**
     * 算法CBC 需要偏移量
     */
    private final static String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 算法ECB 不需要偏移量
     */
    private final static String ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";

    private int bytelength = 256;

    /**
     * 常量字符
     */
    private final String AES = "AES";
    /**
     * 常量字符
     */
    private final String SHA1PRNG = "SHA1PRNG";

    private String resultType = USEBASE64;

    private static final String USEBASE64 = "BASE64";

    private static final String USEHEX = "USEHEX";

    private final String CODE_TYPE = "UTF-8";

    private boolean urlEncoded = false;

    private final String[] consult = new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G"};


    private AESEncrypter(String key) {
        this.KEY = key;
    }

    public AESEncrypter setOffset(String offset) {
        this.OFFSET = offset;
        return this;
    }

    public static AESEncrypter getInstance(String key){
        return new AESEncrypter(key);
    }

    /**
     * 使用ecb模式
     * @return 实体类本身
     */
    public AESEncrypter useEcb() {
        this.ALGORITHMSTR = ECB_PKCS5_PADDING;
        return this;
    }

    /**
     * 使用CBC模式
     * @return 实体类本身
     */
    public AESEncrypter useCbc() {
        this.ALGORITHMSTR = CBC_PKCS5_PADDING;
        return this;
    }

    /**
     * 编码后使用URL编码继续编码,只有Base64会产生URL不和谐的编码16进制是不会产生URL不和谐的编码的，因此只需要在base64相关的编码即可
     */
    public AESEncrypter enableUrlencoded(){
        this.urlEncoded = true;
        return this;
    }

    /**
     * 使用16进制进行二进制的编码
     * @return 本实例
     */
    public AESEncrypter useHexResult() {
        this.resultType = USEHEX;
        return this;
    }

    /**
     * 使用BASE64进行二进制编码
     * @return 本实例
     */
    public AESEncrypter useBase64Result() {
        this.resultType = USEBASE64;
        return this;
    }

    //追加字符
    public  String addStr(int num){
        StringBuffer sbBuffer = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            sbBuffer.append("0");
        }
        return sbBuffer.toString();
    }

    private boolean usingEcb() {
        return this.ALGORITHMSTR.equals(ECB_PKCS5_PADDING);
    }

    private boolean usingCbc() {
        return this.ALGORITHMSTR.equals(CBC_PKCS5_PADDING);
    }

    private void checkKeyParams() throws Exception {
        if (this.KEY.length() != 16){
            throw new Exception("请设置正确的16位长度的key");
        }
        if (usingCbc()){
            // 使用CBC 模式需要设置偏移量
            if (this.OFFSET == null || this.OFFSET.equals("")||this.OFFSET.length()!=16){
                throw new Exception("请设置正确的偏移量，不能为空且是16位的");
            }
        }
    }


    /**
     * 加密
     *
     * @param cleartext
     * @return
     */
    public String encrypt(String cleartext) {
        //加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：1111222233334444
        try {
            byte[] encryptedData = null;
            if (usingCbc()){
                // CBC的加密方式
                IvParameterSpec zeroIv = new IvParameterSpec(OFFSET.getBytes());
                //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
                SecretKeySpec key = new SecretKeySpec(KEY.getBytes(), AES);
                //实例化加密类，参数为加密方式，要写全
                Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
                //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
                //加密时使用:ENCRYPT_MODE;  解密时使用:DECRYPT_MODE;
                cipher.init(Cipher.ENCRYPT_MODE, key,zeroIv); //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
                //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式
                encryptedData = cipher.doFinal(cleartext.getBytes(CODE_TYPE));
            }else {
                KeyGenerator kgen = KeyGenerator.getInstance(AES);
                kgen.init(bytelength);
                Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(), AES));
                encryptedData = cipher.doFinal(cleartext.getBytes(CODE_TYPE));
            }
            if (this.resultType.equals(USEBASE64)){
                if (this.urlEncoded){
                    return URLEncoder.encode(Base64.getEncoder().encodeToString(encryptedData), StandardCharsets.UTF_8.toString());
                }
                return Base64.getEncoder().encodeToString(encryptedData);
            }
            return CommonUtils.bytesToHexString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解密
     *
     * @param encrypted
     * @return
     */
    public String decrypt(String encrypted) {
        byte[] byteMi = null;
        byte[] decryptedData = null;
        try {
            // 如果是使用BASE64加密的则是用base64解密，如果是16进制的，则使用16进制进行解密
            if (resultType.equals(USEBASE64)){
                if (urlEncoded){
                    byteMi = Base64.getDecoder().decode(URLDecoder.decode(encrypted,StandardCharsets.UTF_8.toString()));
                }else {
                    byteMi = Base64.getDecoder().decode(encrypted);
                }
            }else {
                byteMi = CommonUtils.hexToBytes(encrypted);
            }
            if (usingCbc()){
                IvParameterSpec zeroIv = new IvParameterSpec(OFFSET.getBytes());
                SecretKeySpec key = new SecretKeySpec(
                        KEY.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
                //与加密时不同MODE:Cipher.DECRYPT_MODE
                cipher.init(Cipher.DECRYPT_MODE, key,zeroIv);  //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
                decryptedData =  cipher.doFinal(byteMi);
            }else {
                KeyGenerator kgen = KeyGenerator.getInstance(AES);
                kgen.init(128);
                Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(), AES));
                decryptedData = cipher.doFinal(byteMi);
            }
            return new String(decryptedData, CODE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void main(String[] args) {
        String result = AESEncrypter.getInstance("1234567890123456").setOffset("1234567890123456").useCbc().useHexResult().encrypt("1");
        System.out.println(result);
        System.out.println(AESEncrypter
                .getInstance("1234567890123456")
                .setOffset("1234567890123456")
                .useCbc().useBase64Result()
                .decrypt("YY+w9mpYfxI81gg+Iu5fzLBHy+yTZEF1zpce0E56+27zmGKNSJCGOYhsHolcgAa2X4e51g+kOB7GFNPA7SbcChnhfGN8bQp5CMvxyM2PcNyMyXwRULPYov+vE+bUCZVt")
        );
    }


}
