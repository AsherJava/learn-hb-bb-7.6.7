/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.nr.transmission.data.config;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfig {
    private final Integer connectTimeout = 10;
    private final Integer readTimeout = 20;
    public static final String SYNC_RESTTEMPLATE = "syncRestTemplate";

    @Bean(value={"syncRestTemplate"})
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder(new RestTemplateCustomizer[0]);
        return restTemplateBuilder.defaultMessageConverters().setConnectTimeout(Duration.ofSeconds(this.connectTimeout.intValue())).setReadTimeout(Duration.ofSeconds(this.readTimeout.intValue())).build();
    }
}

