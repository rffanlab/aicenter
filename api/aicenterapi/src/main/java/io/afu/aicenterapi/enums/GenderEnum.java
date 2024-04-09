package io.afu.aicenterapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
 * 性别枚举
 * 0 未知
 * 1 男性
 * 2 女性
 * 9 未定义
 */
@Getter
public enum GenderEnum implements BaseEnum<String> {
    UNKNOWN("0", "未知"),
    MALE("1", "男性"),
    FEMALE("2", "女性"),
    UNDEFINED("9", "未定义"),
    ;

    GenderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;


}
