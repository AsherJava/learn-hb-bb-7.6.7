/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.client.RestTemplate
 */
package com.jiuqi.nvwa.dataextract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ZCRestTemplateConfig {
    public static final String ASSET_RESTTEMPLATE = "AssetExtractRestTemplate";

    @Bean(name={"AssetExtractRestTemplate"})
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

