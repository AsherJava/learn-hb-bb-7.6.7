/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package com.jiuqi.common.base.security.inject;

import com.google.common.collect.Maps;
import com.jiuqi.common.base.security.inject.InjectFilter;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectConfig {
    @Value(value="${security.xss.enable:false}")
    private Boolean xssEnabled;
    @Value(value="${security.sql.enable:false}")
    private Boolean sqlEnabled;
    @Value(value="${security.xss.excludes:/favicon.ico,/img/*,/js/*,/css/*}")
    private String excludes;
    private static Boolean sqlAnnotation;

    @Value(value="${security.sql.annotation.enable:true}")
    public void setSqlAnnotation(Boolean sqlAnnotation) {
        InjectConfig.sqlAnnotation = sqlAnnotation;
    }

    @Bean
    public FilterRegistrationBean<InjectFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<InjectFilter> filterRegistrationBean = new FilterRegistrationBean<InjectFilter>();
        filterRegistrationBean.setFilter(new InjectFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        HashMap initParameters = Maps.newHashMap();
        initParameters.put("excludes", this.excludes);
        initParameters.put("isIncludeRichText", "true");
        initParameters.put("xssEnabled", this.xssEnabled.toString());
        initParameters.put("sqlEnabled", this.sqlEnabled.toString());
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

    public static boolean sqlAnnotationEnable() {
        return sqlAnnotation;
    }
}

