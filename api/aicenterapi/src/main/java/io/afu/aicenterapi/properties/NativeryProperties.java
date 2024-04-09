package io.afu.aicenterapi.properties;


import io.afu.aicenterapi.consts.sys.CacheTypeConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取nativery配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "nativery")
@EnableConfigurationProperties
public class NativeryProperties {


    private Doc doc;

    private Log log;

    private Test test;

    private Encryption encryption;


    @Data
    public static class Doc {

        private boolean enabled;

        private boolean open;


    }



    @Data
    public static class Cache {
        private String type = CacheTypeConst.MEMORY;


    }




    @Data
    public static class Log {
        private String path;

        private boolean auto;
    }

    @Data
    public static class Test {
        private boolean enabled;
    }

    @Data
    public static class Encryption {

        private boolean enabled;

        private String key;

        private String iv;

        // 检查一下key和iv 符合AES CBC的要求
        public boolean check() {
            if (key == null || key.length() != 16) {
                return false;
            }
            if (iv == null || iv.length() != 16) {
                return false;
            }
            return true;
        }


    }














}
