package io.afu.aicenterapi.config.sys;


import cn.hutool.core.util.RandomUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * 小刀丝袜哥的配置
 * 业务丝袜哥在native下是不生效的，因此不用担心。
 */
@EnableKnife4j
@ConditionalOnProperty(prefix = "nativery.doc",name = "enabled",havingValue = "true",matchIfMissing = false)
@Slf4j
@Configuration
public class SwaggerConfig {

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags()!=null){
                openApi.getTags().forEach(tag -> {
                    Map<String,Object> map=new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0,100));
                    tag.setExtensions(map);
                });
            }
            if(openApi.getPaths()!=null){
                openApi.addExtension("x-test123","333");
                openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(1,100));
            }

        };
    }


    @Bean
    public OpenAPI customOpenAPI() {
        log.info("开始实例化SwaggerConfig");
        return new OpenAPI()
                .info(new Info()
                .title("Spring Boot Native 管理系统API文档")
                .version("1.0")
                .description( "Spring Boot Native 管理系统，是一款支持生成Native可执行文件包的Spring Boot管理系统。启动速度快，内存占用少（占用堪比go）。既有Java丰富的生态，也有go的低内存占用。")
                .termsOfService("http://doc.xiaominfo.com")
                .license(new License().name("Apache-2.0")
                        .url("http://doc.xiaominfo.com")));
    }

}
