package io.afu.aicenterapi.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.Optional;

/**
 * 通用枚举接口，枚举接口需要实现该接口
 * @param <T>
 */
public interface BaseEnum <T> {

    T getCode();



    String getDesc();


    static <E extends Enum<E> & BaseEnum> E fromCode(String code, Class<E> clazz) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        EnumSet<E> all = EnumSet.allOf(clazz);
        return all.stream().filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

    static <E extends Enum<E> & BaseEnum> Optional<E> optionalFromCode(String code, Class<E> clazz) {
        if (StringUtils.isEmpty(code)) {
            return Optional.empty();
        }
        EnumSet<E> all = EnumSet.allOf(clazz);
        return all.stream().filter(e -> e.getCode().equals(code)).findFirst();
    }


}
