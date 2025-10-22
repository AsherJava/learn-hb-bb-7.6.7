/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package com.jiuqi.gcreport.common.security.csrf;

import com.google.common.collect.Maps;
import com.jiuqi.gcreport.common.security.csrf.CSRFilter;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsrfConfig {
    @Value(value="${security.csrf.enable:false}")
    private Boolean enabled;
    @Value(value="${jiuqi.system.referer:}")
    private String referer;
    @Value(value="${security.csrf.excludes:}")
    private String excludes;

    @Bean
    public FilterRegistrationBean<CSRFilter> csrFilterRegistrationBean() {
        FilterRegistrationBean<CSRFilter> filterRegistrationBean = new FilterRegistrationBean<CSRFilter>();
        filterRegistrationBean.setFilter(new CSRFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(this.enabled);
        filterRegistrationBean.addUrlPatterns("/*");
        HashMap initParameters = Maps.newHashMap();
        initParameters.put("excludes", this.excludes);
        initParameters.put("referer", this.referer);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}

