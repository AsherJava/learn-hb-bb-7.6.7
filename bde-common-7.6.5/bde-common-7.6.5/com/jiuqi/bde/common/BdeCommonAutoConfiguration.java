/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.bde.common;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.bde.common"})
public class BdeCommonAutoConfiguration {
    @Bean(name={"bdeRestTemplate"})
    public RestTemplate bdeRestTemplate() {
        Integer connectTimeout = 300000;
        Integer readTimeout = 300000;
        return this.createRestTemplate(connectTimeout, readTimeout);
    }

    private RestTemplate createRestTemplate(Integer connectTimeout, Integer readTimeout) {
        return new RestTemplateBuilder(new RestTemplateCustomizer[0]).setConnectTimeout(Duration.ofMillis(connectTimeout.intValue())).setReadTimeout(Duration.ofMillis(readTimeout.intValue())).build();
    }
}

