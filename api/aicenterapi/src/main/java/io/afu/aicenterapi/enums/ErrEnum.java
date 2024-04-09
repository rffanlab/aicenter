package io.afu.aicenterapi.enums;

/**
 * 错误枚举接口
 * 自定义错误，请实现该接口，以保证能够被Exception正确的捕获处理
 */
public interface ErrEnum {

    String getName();

    int getCode();

    String getMsg();



}
