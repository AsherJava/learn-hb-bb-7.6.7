/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.filter.ShallowEtagHeaderFilter
 */
package com.jiuqi.budget.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
@PropertySource(value={"classpath:bud-web-cache.properties"})
@ConditionalOnProperty(name={"jiuqi.bud.deploy.type"}, havingValue="war", matchIfMissing=true)
public class BudWebConfig {
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<ShallowEtagHeaderFilter>(new ShallowEtagHeaderFilter(), new ServletRegistrationBean[0]);
        filterRegistrationBean.addUrlPatterns("*.js");
        return filterRegistrationBean;
    }
}

