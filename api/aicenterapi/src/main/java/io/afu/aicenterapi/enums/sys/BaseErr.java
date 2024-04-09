package io.afu.aicenterapi.enums.sys;


import io.afu.aicenterapi.enums.ErrEnum;

public enum BaseErr implements ErrEnum {



    // 第三方相关业务错误
    THIRD_PARTY_ERR("THIRD_PARTY_ERR", 10001, "第三方相关业务错误"),
    // 第三方业务调用失败
    THIRD_PARTY_CALL_ERR("THIRD_PARTY_CALL_ERR", 10002, "第三方业务调用失败"),




    ;

    private String name;

    private int code;

    private String msg;

    BaseErr(String name, int code, String msg) {
        this.name = name;
        this.code = code;
        this.msg = msg;
    }



    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
