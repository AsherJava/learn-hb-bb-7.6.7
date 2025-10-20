/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.servlet.HandlerInterceptor
 *  org.springframework.web.servlet.config.annotation.InterceptorRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.config;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.interceptor.DynamicDataSourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DynamicDataSourceWebConfig
implements WebMvcConfigurer {
    public final DynamicDataSourceInterceptor dynamicDataSourceInterceptor;

    public DynamicDataSourceWebConfig(DynamicDataSourceInterceptor dynamicDataSourceInterceptor) {
        this.dynamicDataSourceInterceptor = dynamicDataSourceInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor)this.dynamicDataSourceInterceptor);
        super.addInterceptors(registry);
    }
}

