/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.servlet.HandlerInterceptor
 *  org.springframework.web.servlet.config.annotation.InterceptorRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
 */
package com.jiuqi.nvwa.sf.adapter.spring;

import com.jiuqi.nvwa.sf.adapter.spring.interceptor.AuthzInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class AuthzInterceptorAppConfig
extends WebMvcConfigurerAdapter {
    AuthzInterceptor authzInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor)this.authzInterceptor).addPathPatterns(new String[]{"/**"}).excludePathPatterns(new String[]{"/**/serverframework/**", "/**/sf/**"});
    }
}

