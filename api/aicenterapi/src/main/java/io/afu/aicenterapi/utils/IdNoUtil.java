package io.afu.aicenterapi.utils;

import cn.hutool.core.util.IdcardUtil;
import io.afu.aicenterapi.enums.GenderEnum;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * @author lostw
 **/
public class IdNoUtil {
    // 权重数组
    private static final int[] WEIGHT_ARR = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
    // 校验位数组
    private static final char[] MOD_ARR = new char[]{'1','0','X','9','8','7','6','5','4','3','2'};

    private final static int ADULT_AGE = 18;

    /**
     * 从身份证中获取年龄, 调用方需保证是合法的身份证
     */
    public static int getAge(@NotNull String idNo) {
        int year = Integer.parseInt(idNo.substring(6, 10));
        int month = Integer.parseInt(idNo.substring(10, 12));
        int day = Integer.parseInt(idNo.substring(12, 14));

        LocalDate now = LocalDate.now();
        int age = now.getYear() - year;
        if ((now.getMonthValue() - month) * 32 + (now.getDayOfMonth() - day) < 0) {
            age -= 1;
        }
        return age;
    }

    /**
     * 判断身份证年龄是否成年
     * @param idNo
     * @return
     */
    public static boolean isAdult(@NotNull String idNo) {
        return getAge(idNo) >= ADULT_AGE;
    }

    /**
     * 获取身份证性别
     * 读取身份证第17位判断
     * @param idNo
     * @return
     */
    public static GenderEnum getGender(@NotNull String idNo) {
        int gender = idNo.charAt(16) - '0';
        return gender % 2 == 0 ? GenderEnum.FEMALE : GenderEnum.MALE;
    }

    /**
     * 检查是否符合身份证格式，最后一位如果是x，强制必须是大写的
     * @param idNo 身份证号码
     */
    public static boolean isValid(String idNo) {
        IdcardUtil.isValidCard18(idNo);
        if (idNo == null || idNo.length() != 18) {
            return false;
        }

        char[] chars = idNo.toCharArray();
        int mod = 0;
        for (int i =0; i < 17; i++) {
            char c = chars[i];
            if (c < '0' || c > '9') {
                return false;
            }
            mod += (c - '0') * WEIGHT_ARR[i];
        }
        mod = mod % 11;
        if (MOD_ARR[mod] != chars[17]) {
            return false;
        }
        return true;
    }

    /**
     * 从身份证获取出生日期
     */
    public static String getBirthday(@NotNull String idNo) {
        String year = idNo.substring(6, 10);
        String month = idNo.substring(10, 12);
        String day = idNo.substring(12, 14);
        return year + "-" + month + "-" + day;
    }
}
