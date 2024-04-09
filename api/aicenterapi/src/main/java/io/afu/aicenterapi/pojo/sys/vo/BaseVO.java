package io.afu.aicenterapi.pojo.sys.vo;


import io.afu.aicenterapi.enums.ConstantEnum;
import io.afu.aicenterapi.exceptions.BaseException;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class BaseVO<T> {


    private boolean status;
    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * 添加自定义fail方法
     * @param msg 消息
     * @param code 代码
     * @return 返回实体类
     */
    public static BaseVO fail(int code, String msg){
        BaseVO<String> BaseVO = new BaseVO<>();
        BaseVO.setStatus(false);
        BaseVO.setCode(code);
        BaseVO.setMsg(msg);
        return BaseVO;
    }


    public static BaseVO fail(BaseException e){
        BaseVO<String> BaseVO = new BaseVO<>();
        BaseVO.setStatus(false);
        BaseVO.setCode(e.getCode());
        BaseVO.setMsg(e.getMessage());
        return BaseVO;
    }

    public static BaseVO fail(Exception e) {
        BaseVO<String> BaseVO = new BaseVO<>();
        BaseVO.setStatus(false);
        BaseVO.setCode(-1);
        BaseVO.setMsg(BaseVO.getExceptionMessage(e));
        return BaseVO;
    }

    public static BaseVO fail(String msg) {
        BaseVO<String> BaseVO = new BaseVO<>();
        BaseVO.setStatus(false);
        BaseVO.setCode(-1);
        BaseVO.setMsg(msg);
        return BaseVO;
    }

    public static BaseVO fail(ConstantEnum constantEnum){
        BaseVO<String> BaseVO = new BaseVO<>();
        BaseVO.setStatus(false);
        BaseVO.setCode(constantEnum.getCode());
        BaseVO.setMsg(constantEnum.getMsg());
        return BaseVO;
    }


    public static String getExceptionMessage(Throwable e){
        if (e == null) {
            return "内部错误";
        }
        String causeMessage = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null){
            if (causeMessage == null) {
                causeMessage  = "";
            }
            causeMessage = BaseVO.getExceptionMessage(e.getCause());
        }
        return causeMessage;
    }



    public static BaseVO success(){
        BaseVO<String> BaseVO = new BaseVO<>();
        BaseVO.setStatus(true);
        BaseVO.setCode(ConstantEnum.OPERATION_SUCCESS.getCode());
        BaseVO.setMsg(ConstantEnum.OPERATION_SUCCESS.getMsg());
        return BaseVO;
    }

    public static BaseVO success(String data){
        BaseVO<String> BaseVO = new BaseVO<>();
        BaseVO.setStatus(true);
        BaseVO.setCode(ConstantEnum.OPERATION_SUCCESS.getCode());
        BaseVO.setMsg(ConstantEnum.OPERATION_SUCCESS.getMsg());
        BaseVO.setData(data);
        return BaseVO;
    }

    /**
     * 通过泛型来进行参数的返回的解析
     * @param k 泛型传入的data
     * @param <K> 泛型定义传入的data的类型
     * @return 返回返回实体类
     */
    public static <K> BaseVO success(K k){
        BaseVO<K> BaseVO = new BaseVO<>();
        BaseVO.setStatus(true);
        BaseVO.setCode(ConstantEnum.OPERATION_SUCCESS.getCode());
        BaseVO.setMsg(ConstantEnum.OPERATION_SUCCESS.getMsg());
        BaseVO.setData(k);
        return BaseVO;
    }


    public boolean hasSuccess() {
        return ConstantEnum.OPERATION_SUCCESS.getCode()== this.code;
    }





}
