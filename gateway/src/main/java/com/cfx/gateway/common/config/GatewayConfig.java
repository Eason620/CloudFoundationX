package com.cfx.gateway.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@Data
@Configuration
@ConfigurationProperties("config.gateway")
public class GatewayConfig {


    private String oauth2Security = "cfx:oauth2:security";
    /**
     * 是否跳过授权，默认不跳过
     */
    private boolean skip;
    /**
     * 默认跳过授权api集合
     */
    private Set<String> defaultSkipApis;
    /**
     * 跳过授权
     */
    private Set<String> skipApis;

}
