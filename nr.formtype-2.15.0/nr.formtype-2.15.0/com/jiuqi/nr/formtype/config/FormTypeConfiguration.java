/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.config;

import com.jiuqi.nr.formtype.config.FormTypeCacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"com.jiuqi.nr.formtype.internal", "com.jiuqi.nr.formtype.org.extend", "com.jiuqi.nr.formtype.web.rest"})
@Configuration
public class FormTypeConfiguration {
    @Bean
    public FormTypeCacheConfig getFormTypeCacheConfig() {
        return new FormTypeCacheConfig();
    }
}

