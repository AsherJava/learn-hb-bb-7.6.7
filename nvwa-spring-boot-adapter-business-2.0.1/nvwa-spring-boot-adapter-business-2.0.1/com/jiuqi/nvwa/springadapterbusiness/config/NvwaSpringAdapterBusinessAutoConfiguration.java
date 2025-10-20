/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.springadapterbusiness.config;

import com.jiuqi.nvwa.springadapterbusiness.util.SpringAdapterBusinessBeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class NvwaSpringAdapterBusinessAutoConfiguration {
    @Bean
    @Lazy(value=false)
    public SpringAdapterBusinessBeanUtils springAdapterBusinessBeanUtils() {
        return new SpringAdapterBusinessBeanUtils();
    }
}

