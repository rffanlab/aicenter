package io.afu.aicenterapi.exceptions;


import io.afu.aitaskservice.enums.ErrEnum;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class BaseException extends RuntimeException {


    private static final long serialVersionUID = 2342341341L;

    private int code;

    public BaseException(){
        super();
    }

    public BaseException(String message){
        super(message);
    }

    public BaseException(int code, String message){
        super(message);
        this.code = code;
    }


    public BaseException(ErrEnum errEnum) {
        super(errEnum.getMsg());
        this.code = errEnum.getCode();
    }

    public BaseException(Throwable cause){
        super(cause);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }




}
