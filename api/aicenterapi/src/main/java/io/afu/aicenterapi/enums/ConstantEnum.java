package io.afu.aicenterapi.enums;

public enum ConstantEnum implements ErrEnum {
    // 通用相关
    OPERATION_SUCCESS("OPERATION_SUCCESS",200,"操作成功"),
    OPERATION_FAILED("OPERATION_FAILED",-1,"操作失败"),
    INTERNAL_ERROR("INTERNAL_ERROR",-2,"内部错误"),
    // 用户相关
    USER_NOT_EXIST("USER_NOT_EXIST",100001,"用户不存在"),
    USER_PASS_NOT_MATCH("USER_PASS_NOT_MATCH",100002,"用户名密码不匹配"),
    USER_PASS_ERR_EXCEED("USER_PASS_ERR_EXCEED",100003,"用户密码错误超过限额次数，IP和用户将被锁定"),
    NOT_CHINA_MAINLAND_IDCARD("NOT_CHINA_MAINLAND_IDCARD",100004,"非中国大陆身份证号码"),
    NOT_CHINA_MAINLAND_PHONE_NUM("NOT_CHINA_MAINLAND_PHONE_NUM",100005,"用户手机号码非中国大陆手机号码"),
    USER_NOT_ACTIVED("USER_NOT_ACTIVED",100006,"该用户未激活"),
    USER_SUSPEND("USER_SUSPEND",100007,"该用户已经被禁用"),
    BIRTH_YEAR_ILLEGAL("BIRTH_YEAR_ILLEGAL",100008,"用户出生日期不合法"),
    IDCARD_ILLEGAL("IDCARD_ILLEGAL",100009,"身份证不合法"),
    USER_SESSION_EXPIRED("USER_SESSION_EXPIRED",100010,"用户会话已经过期"),
    USER_AUTH_FAILED("USER_AUTH_FAILED",100011,"用户没有访问权限"),
    USER_REG_WITHOUT_INVITE_CODE("USER_REG_WITHOUT_INVITE_CODE",100011,"用户没有邀请码，无法注册"),
    USER_VERIFY_CODE_NOT_MATCH("USER_VERIFY_CODE_NOT_MATCH",100012,"用户手机验证码不匹配"),
    USER_NAME_OR_MOBILE_ALREADY_EXIST("USER_NAME_OR_MOBILE_ALREADY_EXIST",100013,"用户名或者手机号码已经存在了"),
    USER_VERIFY_CODE_NOT_FOUND_OR_ALREADY_USED("USER_VERIFY_CODE_NOT_FOUND_OR_ALREADY_USED",100014,"用户验证码没有找到或者已经被使用"),

    // 用户标记
    USER_FRONT("USER_FRONT",100011,"前端用户"),
    USER_BACKEND("USER_BACKEND",100012,"后端用户"),

    // 用户角色相关
    USER_ROLE_NOT_EXIST("USER_ROLE_NOT_EXIST",110001,"该角色不存在"),
    USER_ROLE_ALREADY_EXIST("USER_ROLE_ALREADY_EXIST",110002,"该角色已经存在"),

    // 用户访问权限相关
    USER_PERMISSION_DENIED("USER_PERMISSION_DENIED",120001,"用户权限不足"),
    USER_PERMISSION_GRANT("USER_PERMISSION_GRANT",120002,"用户授权成功"),


    // 用户第三方相关的
    USER_THIRD_EXIST("USER_THIRD_EXIST",110001,"用户第三方配置已存在"),
    USER_THIRD_NOT_EXIST("USER_THIRD_NOT_EXIST",110002,"用户第三方配置不存在"),

    // 参数相关常量
    PARAM_NOT_SET("PARAM_NOT_SET",110001,"参数没有设置"),
    PARAM_TYPE_ERROR("PARAM_TYPE_ERROR",110002,"参数类型错误"),
    PARAM_ERROR("PARAM_ERROR",110003,"参数错误"),

    PARAM_CLASS_TYPE_NOT_SET("PARAM_CLASS_TYPE_NOT_SET",110004,"参数：类的类型没有设置"),



    // 系统相关常量120000
    CHARSET_UNRECOGNIZE("CHARSET_UNRECOGNIZE",120001,"未识别的编码"),
    // 设置相关常量 121000
    SETTING_ALREADY_EXIST("SETTING_ALREADY_EXIST",121001,"设置已经存在"),
    SETTING_NOT_EXIST("SETTING_NOT_EXIST",121002,"设置并不存在，请配置该设置"),
    SETTING_NOT_DELETEABLE("SETTING_NOT_DELETEABLE",121003,"该设置不可删除，或者该设置是系统设置"),



    // API 系统相关常量
    API_NOT_REG("API_NOT_REG",200001,"应用并未注册或者应用并没有启用。"),
    API_ALREADY_EXIST("API_ALREADY_EXIST",200002,"应用已经存在同名应用请勿重复添加。"),
    API_KEY_SECRET_NOT_MATCH("API_KEY_SECRET_NOT_MATCH",200003,"应用的Key和Secret 并不匹配。"),
    API_REACH_LIMIT("API_REACH_LIMIT",200004,"API访问受限，访问次数受限。"),
    API_HAS_NO_PERMISSION("API_NO_PERMISSION",200005,"没有权限访问该应用。"),
    API_SIGN_NOT_MATCHED("API_SIGN_NOT_MATCHED",200006,"签名校验不通过"),
    API_SESSION_EXPIRED("API_SESSION_EXPIRED",200007,"接口授权过期，请重新获取登录授权"),
    API_METHOD_NOT_SPECIFICED("API_METHOD_NOT_SPECIFICED",200008,"接口方法名没有指定"),
    API_STILL_HAS_APP_USING_IT_CLOUD_NOT_BEEN_DELETED("API_STILL_HAS_APP_USING_IT_CLOUD_NOT_BEEN_DELETED",200009,"尚有应用在使用该服务，无法被移除"),


    // 机构或者应用已经存在
    ORG_ALREADY_EXIST("ORG_ALREADY_EXIST",210001,"机构或者应用已经存在，请勿重复添加"),
    // 机构或者应用不存在
    ORG_NOT_EXIST("ORG_NOT_EXIST",210002,"机构或者应用不存在,请添加后再进行操作"),
    // 内部应用交互码错误
    ORG_INNNER_TRANSCODE_FAILED("ORG_INNNER_TRANSCODE_FAILED",210003,"内部应用交互码错误"),
    // 机构给该服务的拓展信息已存在
    ORG_EXT_INFO_EXIST("ORG_EXT_INFO_EXIST",210003,"机构给该服务的拓展信息已存在"),
    // 该机构，该服务下的拓展配置不存在
    ORG_EXT_INFO_NOT_EXIST("ORG_EXT_INFO_NOT_EXIST",210004,"该机构给该服务的拓展并不存在"),








    // 重定向相关
    REDIRECTION_NOT_FOUND("REDIRECTION_NOT_FOUND",210001,"重定向并没有找到"),





    // 操作系统相关常量
    SERVICE_EXECUTE_PATH_MUST_BE_ABSOLUTE_PATH("SERVICE_EXECUTE_PATH_MUST_BE_ABSOLUTE_PATH",210001,"系统服务的启动路径必须是绝对路径"),
    // 操作系统文件常量
    FILE_NOT_EXIST("FILE_NOT_EXIST",220001,"文件不存在"),
    FILE_UPLOAD_FAILED("FILE_UPLOAD_FAILED",220002,"文件上传失败"),
    FILE_NOT_PERMISSION_TO_CREATE_DIR("FILE_NOT_PERMISSION_TO_CREATE_DIR",220003,"没有权限创建目录"),


    // 网络相关 230000
    REQUEST_URL_NOT_SET("REQUEST_URL_NOT_SET",230001,"请求的URL没有设置"),
    REQUEST_PARAM_NOT_SET("REQUEST_PARAM_NOT_SET",230002,"请求参数未设置"),

    //文章相关 300000
    POST_NOT_FOUND("POST_NOT_FOUND",300001,"文章并没有找到，请确认一下"),
    POST_GRANT_NEEDED("POST_GRANT_NEED",300002,"文章需要授权才能访问"),
    POST_ACCESS_DENIED("POST_ACCESS_DENIED",300003,"文章访问首先，该文章并不支持对外访问"),

    // 支付相关常量
    PAY_AMOUNT_ILLEGAL("PAY_AMOUNT_ILLEGAL",400001,"支付金额非法"),
    PAY_GATWAY_FAILE("PAY_GATWAY_FAILE",400002,"支付网关失败"),
    PAY_BALANCE_NOT_ENOUGH("PAY_BALANCE_NOT_ENOUGH",400003,"支付余额不足"),


    // 文章相关




    // 机器人相关的错误
    FILE_TO_CONVERT_NOT_FOUND("FILE_NOT_FOUND",600001,"需要转换的文件找不到"),
    REMOTE_API_DEAD("REMOTE_API_DEAD",600002,"远程API挂了请联系管理员"),





    // 运维相关 700000开始
    // Nginx 相关
    NGINX_UNIX_UPSTREAM_MUST_BE_UNIX_SOCK_FILE("NGINX_UNIX_UPSTREAM_MUST_BE_UNIX_SOCK_FILE",700001,"Nginx的反代目标的如果是unixsock必须是.sock的文件"),

    // 企业相关
    COMPANY_NOT_FOUND("COMPANY_NOT_FOUND",800001,"企业没找到"),
    COMPANY_NOT_ACTIVED("COMPANY_NOT_ACTIVATED",800002,"该企业并没有激活"),


    // 京东相关

    // 淘宝相关

    // 900000 开始第三方相关的提示和错误
    // 910000 DNSPOD
    DNSPOD_DOMAIN_NOT_FOUND("DNSPOD_DOMAIN_NOT_FOUND",910001,"需要请求的域名未找到"),
    DNSPOD_DOMAIN_HAS_NO_PERMISSION("DNSPOD_DOMAIN_HAS_NO_PERMISSION",910002,"没有权限执行该域名的请求"),
    DNSPOD_DOMAIN_OPERATION_SUCCESS("DNSPOD_DOMAIN_OPERATION_SUCCESS",910003,"域名操作成功"),
    DNSPOD_RECORD_NOT_FOUND("DNSPOD_RECORD_NOT_FOUND",910004,"该域名的解析记录没有找到"),
    DNSPOD_RECORD_OPERATION_SUCCESS("",910005,"记录操作成功"),

    // 911000 aliyun相关的
    // 912000 腾讯相关的



    ;






    ;


    private String name;

    private int code;

    private String msg;

    ConstantEnum(String name, int code, String msg) {
        this.name = name;
        this.code = code;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
