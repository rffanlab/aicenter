package io.afu.aicenterapi.utils;



import io.afu.aicenterapi.exceptions.BaseException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class StringUtils {


    public static String getPriceFromStr(String str){
        Pattern p = Pattern.compile("[0-9]\\d*\\.?\\d*");
        Matcher m = p.matcher(str);
        if (m.find()){
            System.out.println("args = [" + m.group() + "]");
            return m.group();
        }
        return null;
    }


    public static String strDoubleSetTwoScale(String strDouble){
        return (new BigDecimal(strDouble)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * Get Short Area Code
     * @param fullAreaCode
     * @return
     */
    public String getCurrentShortAreaCode(String fullAreaCode) {
        char[] characters = fullAreaCode.toCharArray();
        int idx = characters.length-1;
        boolean meetNoZero = false;
        while (!meetNoZero){
            if (characters[idx]=='0'){
                idx -=1;
            }else {
                meetNoZero = true;
            }
        }
        return new String(Arrays.copyOfRange(characters,0,idx+1));
    }



    /**
     * 生成随机字符串
     * @param length 长度
     * @return String 生成好的字符串
     */
    public static String randStr(Integer length){
        String Chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(Chars.charAt(num));
        }
        return buf.toString();
    }

    /**
     * 16进制字符串 转换为bytes
     * @param str 16进制字符串
     * @return bytes
     */
    public static byte[] hexStrToBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * 通过子域名或者URL来获取根域名
     * @param urlOrDomain url或者域名
     * @return 根域名
     */
    public static String getRootDomain(String urlOrDomain){
        String tmp = urlOrDomain.replace("https://","").replace("http://","");
        String[] strs = tmp.split("/");
        String theDomain = strs[0];
        int lastDotIndex = theDomain.lastIndexOf(".");
        if (lastDotIndex == -1){
            return null;
        }
        String suffix = theDomain.substring(lastDotIndex);
        String left = theDomain.substring(0,lastDotIndex);
        int last2DotIndex = left.lastIndexOf(".");
        if (last2DotIndex == -1){
            return null;
        }
        String name = left.substring(last2DotIndex+1);
        return name+suffix;
    }


    /**
     * 出现的某个字符的次数
     * @param srcText 需要查询的文本
     * @param findText 目标字符串文本
     * @return 数量
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 判断数值是否是奇数
     * @param i 数值
     * @return 布尔值
     */
    public static boolean isOdd(int i) {
        return (i & 1) != 0;
    }


    public static Boolean isEmpty(String theStr){
        return null == theStr || "".equals(theStr.trim());
    }

    public static Boolean isNotEmpty(String theStr){
        return null != theStr && !"".equals(theStr.trim());
    }

    /**
     * 检查是否是正常是字符串
     * @param strToVerify  需要检查的字符串
     * @return boolean
     */
    public static boolean isNormalString(String strToVerify){
        String strRegex = "^[A-Za-z0-9\\-]+$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(strToVerify);
        return matcher.find();
    }

    /**
     * 判断是否是url
     * @param strToVerify 待检查的字符串
     * @return boolean
     */
    public static boolean isUrlString(String strToVerify){
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//对比
        Matcher mat = pat.matcher(strToVerify.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }


    /**
     * 通过md5值加密字符串
     * @param str 带加密的字符串
     * @return 加密后的字符串
     * @throws Exception 抛出的错误
     */
    public static String md5Str(String str) throws Exception{
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return new BigInteger(1,messageDigest.digest()).toString(16);
        }catch (Exception e){
            throw new Exception(e);
        }
    }


    public static String randToken(){
        return randStr(32);
    }

    /**
     * 将html字符串的br转换为p
     * @param source 待转换的原始字符串
     * @return 转换后的字符串
     */
    public static String changeBrToP(String source){
        String output = "";
        source = source.replace("<br/>","<br>");
        source = source.replace("<br />","<br>");
        String[] lists = source.split("<br>");
        for (String line:lists){
            String newLine = "";
            String tline = line.trim();
            if (!tline.equals("")){
                newLine = "<p>"+tline+"</p>";
                output = output + newLine;
            }
        }
        return output.replace("&nbsp;","");
    }

    /**
     * 通过文章标题获取标题中的数字
     * @param title 标题
     * @return 返回排行
     */
    public static Integer getTitleNum(String title){
        String rgex = "第(.*?)章";
        Pattern pattern = Pattern.compile(rgex);
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()){
            Integer orderNum = chineseNumToInt(matcher.group(1));
            return orderNum;
        }
        rgex = "(.*?)章";
        pattern = Pattern.compile(rgex);
        matcher = pattern.matcher(title);
        if (matcher.find()){
            Integer orderNum = chineseNumToInt(matcher.group(1));
            return orderNum;
        }
        rgex = "第(.*?)掌";
        pattern = Pattern.compile(rgex);
        matcher = pattern.matcher(title);
        if (matcher.find()){
            Integer orderNum = chineseNumToInt(matcher.group(1));
            return orderNum;
        }

        return -1;
    }

    /**
     * 改方法能将中文字符串数值转换为Int的数值。如报错，请提供数值字符串我去还原现场，毕竟一个小时的产物，可能思虑不周，请见谅
     * @param num 带转换的数值
     * @return 数值
     */
    public static Integer chineseNumToInt(String num){
        System.out.println("num = [" + num + "]");
        Integer resultNum = 0;
        String[] list = num.split("");
        Integer tmpNum = 0;
        Integer mark = 0;
        for (int i =0;  i< list.length;i++ ){
            String str = list[i];
            if (isQuantifier(str)){
                mark = 1;
            }
            if (isCNNumber(str)){
                mark = 0;
            }
            if (mark.equals(0)){
                resultNum = resultNum + tmpNum;
                tmpNum = 0;
                switch (str){
                    case "一":
                        tmpNum = 1;
                        break;
                    case "二":
                        tmpNum = 2;
                        break;
                    case "三":
                        tmpNum = 3;
                        break;
                    case "四":
                        tmpNum = 4;
                        break;
                    case "五":
                        tmpNum = 5;
                        break;
                    case "六":
                        tmpNum = 6;
                        break;
                    case "七":
                        tmpNum = 7;
                        break;
                    case "八":
                        tmpNum = 8;
                        break;
                    case "九":
                        tmpNum = 9;
                        break;
                    case "零":
                        tmpNum = 0;
                        break;
                    case "壹":
                        tmpNum = 1;
                        break;
                    case "贰":
                        tmpNum = 2;
                        break;
                    case "叁":
                        tmpNum = 3;
                        break;
                    case "肆":
                        tmpNum = 4;
                        break;
                    case "伍":
                        tmpNum = 5;
                        break;
                    case "陆":
                        tmpNum = 6;
                        break;
                    case "柒":
                        tmpNum = 7;
                        break;
                    case "捌":
                        tmpNum = 8;
                        break;
                    case "玖":
                        tmpNum = 9;
                        break;
                    default:
                        tmpNum=0;
                        break;
                }
                if (i == list.length-1){
                    resultNum = resultNum + tmpNum;
                }

            }else if (mark.equals(1) || mark.equals(2)){
                if (i==0){
                    tmpNum = 1;
                }
                switch (str){
                    case "圆":
                        tmpNum = tmpNum * 1;
                        break;
                    case "十":
                        tmpNum = tmpNum * 10;
                        break;
                    case "拾":
                        tmpNum = tmpNum  * 10 ;
                        break;
                    case "百":
                        tmpNum = tmpNum * 100;
                        break;
                    case "佰":
                        tmpNum = tmpNum * 100;
                        break;
                    case "千":
                        tmpNum = tmpNum * 1000;
                        break;
                    case "仟":
                        tmpNum  = tmpNum * 1000;
                        break;
                    case "万":
                        tmpNum = tmpNum * 10000;
                        break;
                    case "萬":
                        tmpNum = tmpNum * 10000;
                        break;
                    case "亿":
                        tmpNum = tmpNum * 100000000;
                        break;

                }
                if (i==0){
                    resultNum = resultNum +tmpNum;
                    tmpNum = 0;
                }
                if (i == list.length-1){
                    resultNum = resultNum + tmpNum;
                }

            }
        }
        System.out.println("num = [" + resultNum + "]");

        return resultNum;
    }

    public static Boolean isBlank(String str){
        return str == null || str.equals("");
    }

    /**
     * 判断是否是中文数字
     * @param str 带判断的字符串
     * @return boolean
     */
    public static Boolean isQuantifier(String str){
        String[] quantifier = {"圆","十","拾","百","佰","千","仟","万","萬"};
        for (String qStr:quantifier){
            if (qStr.equals(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是中文数字
     * @param str 待判断的字符串
     * @return boolean
     */
    public static Boolean isCNNumber(String str){
        String[] CNNumber = {"玖","捌","柒","陆","伍","肆","叁","贰","壹","零","九","八","七","六","五","四","三","二","一"};
        for (String cnNum:CNNumber){
            if (cnNum.equals(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取域名网址
     * @param url 待获取的网址
     * @return 获取的网址
     */
    public static String getBaseUrl(String url){
        String[] strs = url.split("://");
        String protocol = strs[0];
        String domain = strs[1].split("/")[0];
        return protocol+"://"+domain;
    }

    /**
     * 将url处理为完整的URL，注意！要处理的url必须是从baseUrl中获取的，否则会出错！
     * @param urlToDeal 需要处理的url
     * @param baseUrl 基础url
     * @return 完整的URL
     */
    public static String makeUrlFull(String urlToDeal,String baseUrl){
        if (urlToDeal.startsWith("/")){
            return getBaseUrl(baseUrl)+urlToDeal;
        }else if (urlToDeal.startsWith("http://")){
            return urlToDeal;
        }else if (urlToDeal.startsWith("https://")){
            return urlToDeal;
        }else {
            return baseUrl+urlToDeal; // 例如http://www.baidu.com/23333 这个是baseUrl ,www/www/www 这个是urlToDeal 那么就可以两个组合一起，
        }
    }

    public static String addUrlProtocol(String url,String protocol){
        if (url.startsWith("http")||url.startsWith("https")){
            return url.replace("\r","").replace("\n","");
        }else {
            if (url.startsWith("//")){
                return (protocol+":"+url).replace("\r","").replace("\n","");
            }else {
                return (protocol+"://"+url).replace("\r","").replace("\n","");
            }
        }
    }

    /**
     * 解析ids到列表id
     * @param ids 待解析的列表
     * @return 列表
     */
    public static List<Long> parseIdsToIntIds(String ids){
        List<Long> intIds = new ArrayList<>();
        String[] strIds = ids.split(",");
        for (String id:strIds){
            Long tmp = Long.valueOf(id);
            intIds.add(tmp);
        }
        return intIds;
    }


    /**
     * 将时间转为2018-01-01 11:11:11 这种格式
     * @param time 待转换的时间
     * @return 转换后的时间
     */
    public static String timeToStr(Date time){
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFm.format(time);
    }

    /**
     * 转换p到r
     * @param str 待转换的字符串
     * @return 转换后的字符串
     */
    public static String parsePtoR(String str){
        str = str.replace("<p>","").replace("&nbsp;","").replace("</p>","\n").replace("&nbsp;","");
        return str;
    }

    /**
     * 将带P的html文本转换成文本
     * @param str 文本
     * @return 返回列表
     */
    public static String[] parsePtoList(String str){
        String[] dealed = str.replace("<p>","").replace("&nbsp;","").split("</p>");
        return dealed;
    }

    /**
     * 将阿拉伯数字转换为中文的一二三四
     * @param num 数字
     * @return 返回中文的数字
     */
    public static String translateIntNumToStr(Integer num){
        String word = "";
        if (num>=10000 && num <100000000){
            Integer tmp = num / 10000;
            word = tmp.toString()+"万字";
        }else if (num >100000000){
            Integer tmp = num / 100000000;
            word = tmp.toString()+"亿字";
        }else {
            word = num.toString()+"字";
        }
        return word;
    }

    /**
     * 是否包含数值
     * @param text 字符串文本
     * @return true为包含，false为不包含
     */
    public static Boolean containsNum(String text){
        String pRule = ".*\\d+.*";
        Pattern p = Pattern.compile(pRule);
        Matcher m = p.matcher(text);
        return m.find();
    }

    /**
     * 通过URL获取URL中包含的文件名
     * @param url 需要处理的URL
     * @return 文件名
     */
    public static String getFileNameByUrl(String url) {
        String fileName = null;
        String[] strs = url.split("\\?");
        String urlWithoutParam = strs[0];
        System.out.println(strs[0]);
        int lastIndex = urlWithoutParam.lastIndexOf("/");
        fileName = urlWithoutParam.substring(lastIndex+1);
        return fileName;
    }


    public static String[] STRING_SPLITER = {",","，","。","\n","\r"," "};

    public static String[] COUPON_NOTICE = {"优惠码"};


    public static List<String> getSmaillLineWithStr(String containStr,String fullText) {
        List<String> strs = new ArrayList<>();
        int index = fullText.indexOf(containStr);
        if (index == -1){
            return strs;
        }
        String nextStr = fullText.substring(index);
        String preStr = fullText.substring(0,index);
        int nextInt = -1;
        int preInt = -1;
        for (String spliter:STRING_SPLITER){
            int tmpNextIndex = nextStr.indexOf(spliter);
            if (nextInt == -1){
                nextInt = tmpNextIndex;
            }
            if (nextInt != -1 && nextInt > tmpNextIndex && tmpNextIndex != -1){
                nextInt = tmpNextIndex;
            }
            int tmpPreIndex = preStr.lastIndexOf(spliter);
            if (preInt == -1){
                preInt = tmpPreIndex;
            }
            if (preInt != -1 && preInt<tmpPreIndex && tmpPreIndex != -1){
                preInt= tmpPreIndex;
            }
        }
        if (preInt == -1){
            preInt = 0;
        }
        if (nextInt == -1){
            nextInt = fullText.length();
        }else {
            nextInt += index;
        }
        strs.add(fullText.substring(preInt,nextInt));
        String leftStr = fullText.substring(nextInt);
        List<String> getLeft = getSmaillLineWithStr(containStr,leftStr);
        if (getLeft.size()>0){
            strs.addAll(getLeft);
        }
        return strs;
    }



    public static void checkVersionType(String ver) throws BaseException {
        String[] strList = ver.split("\\.");
        for (String str:strList){
            try{
                Integer v = Integer.valueOf(str);
            }catch (Exception e){
                throw new BaseException("您传入的版本的值不对，版本值仅支持如下格式\" xxx.xxx.xxx  其中xxx是数值\"");
            }
        }
    }

    /**
     * 移除相关的emoji 表情
     * @param source 原始字符串
     * @return 移除emoji 表情后的字符串
     */
    public static String noEmoji(String source) {
        if(source != null)
        {
            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find())
            {
                source = emojiMatcher.replaceAll("*");
                return source ;
            }
            return source;
        }
        return source;
    }

    /**
     * 将URL上的查询字符串转换为Map
     * @param queryString URL上的查询字符串
     * @return 返回Map
     */
    public static Map<String,String> queryStringToMap(String queryString) {
        Map<String,String> map = new HashMap<>();
        if (queryString == null || queryString.length() == 0){
            return map;
        }
        String[] items = queryString.split("&");
        for (String item:items){
            String[] kv = item.split("=");
            if (kv.length == 2){
                map.put(kv[0],kv[1]);
            }
        }
        return map;
    }

    /**
     * 将url上的查询字符串转换为实体类属性。（该方法仅能做基础类型及其封装类型的转换，更高以及的无法操作，可以使用jackson来执行转换。）
     * 这里可能会有一个问题，直接使用反射的方式，如果属性的乐星和设置的类型不一致，就会出问题。
     * 本质上queryString 是一个线性的数据结构，也就是一组平滑的key，value数据结构，因此仅仅支持当前传入的实体类进行设置入参即可。
     * @param queryString url上的查询字符串
     * @param clazz 需要转换的实体类
     * @return 当前实体类的实例化
     * @param <T> 实体类定义
     * @throws Exception
     */
    public  static <T> T queryStringToObject(String queryString,Class<T> clazz) throws Exception {
        T t = clazz.cast(clazz.getDeclaredConstructor().newInstance());
        if (queryString == null || queryString.length() == 0){
            return null;
        }
        Map<String,String> map = queryStringToMap(queryString);
        for (Map.Entry<String,String> entry:map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            Field field = null;
            try {
                field = clazz.getDeclaredField(key);
            }catch (NoSuchFieldException noSuchFieldException){
                // 值得指出的是，这可能就是jackson json序列化的时候属性没有定义，就会报错的原因吧。
                continue;
            }
            field.setAccessible(true);
            // 获取到field的类型，并尝试将value转换为对应的类型
            Class<?> type = field.getType();
            try {
                if (type == String.class) {
                    field.set(t, value);
                }else if (type == Integer.class || type == int.class){
                    field.set(t,Integer.valueOf(value));
                }else if (type == Long.class || type == long.class){
                    field.set(t,Long.valueOf(value));
                }else if (type == Double.class || type == double.class){
                    field.set(t,Double.valueOf(value));
                }else if (type == Float.class || type == float.class) {
                    field.set(t, Float.valueOf(value));
                }else if (type == Boolean.class || type == boolean.class) {
                    field.set(t, Boolean.valueOf(value));
                }else if (type == Short.class || type == short.class) {
                    field.set(t, Short.valueOf(value));
                }else if (type == Byte.class || type == byte.class) {
                    field.set(t, Byte.valueOf(value));
                }else if (type == Character.class || type == char.class) {
                    field.set(t, value.charAt(0));
                }else {
                    // 其他类型暂时不支持
                    continue;
                }
            }catch (Exception e){
                // 这里不做任何操作，毕竟如果是不同的类型，就会出现异常，这里就不做处理了。
                e.printStackTrace();
            }
        }
        return t;
    }




}
