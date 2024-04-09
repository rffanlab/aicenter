package io.afu.aicenterapi.controller;

import io.afu.aitaskservice.enums.ConstantEnum;
import io.afu.aitaskservice.exceptions.BaseException;
import io.afu.aitaskservice.pojo.sys.vo.BaseVO;
import io.afu.aitaskservice.utils.WebHttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {



    private Logger logger = LoggerFactory.getLogger(getClass());


    private void logErrRequestBody() {
        HttpServletRequest request = WebHttpUtil.getRequest();
        if (request != null) {
            String header = WebHttpUtil.parseRequestHeaders(request);
            String param = WebHttpUtil.parseParams(request);
            String body = WebHttpUtil.parseBodyParams(request);
            String ip = WebHttpUtil.getIP(request);
            logger.error("报错接口请求信息: \n" +
                    "请求ip：[" + ip + "]\n" +
                    "请求头：[" + header + "]\n" +
                    "请求头参数：[" + param + "]\n" +
                    "请求体参数：[" + body + "]\n");
        }

    }

    @ExceptionHandler({BaseException.class})
    public BaseVO handlerException(BaseException e, HttpServletResponse response){
        logger.info("[接口访问出错]",e);
        logErrRequestBody();
        if (ConstantEnum.USER_SESSION_EXPIRED.getCode()==e.getCode()){
            response.setStatus(401);
        }
        if ("403".equals(e.getCode())){
            response.setStatus(403);
        }
        return BaseVO.fail(e);
    }



    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseVO handlerException(MethodArgumentNotValidException e){
        logErrRequestBody();
        StringBuilder stringBuilder = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach( err -> stringBuilder.append("参数：").append(err.getField()).append(":").append(err.getDefaultMessage()).append(";"));
        return BaseVO.fail(ConstantEnum.PARAM_ERROR.getCode(),stringBuilder.toString());
    }

    @ExceptionHandler({ Exception.class })
    public BaseVO exception(Exception e) {
        logger.error(e.getMessage(),e);
        logErrRequestBody();
        return BaseVO.fail(e);
    }


}
