package io.afu.aicenterapi.enums.business;

import io.afu.aicenterapi.enums.BaseEnum;
import lombok.Getter;

public interface LLMEnum {


    @Getter
    public static enum Type implements BaseEnum<String> {

        WENXIN_YIYAN_BAIDU("wenxin_yiyan_baidu", "百度文心一言"),
        CHATGPT_OPENAI("chatgpt_openai", "OpenAI ChatGPT"),
        CHATGPT_AZURE("chatgpt_azure", "Azure ChatGPT"),
        XINGHUO_XUNFEI("xinghuo_xunfei", "讯飞星火"),
        TONGYIQIANWEN_ALIYUN("tongyiqianwen_aliyun", "阿里云通义千问"),
        CHATGLM_ZP("chatglm_zp", "ChatGLM 智普清华"),
        LLAMA("llama", "META Llama"),



        ;

        Type(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        String code;

        String desc;

    }





}
