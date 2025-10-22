/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.customentry.config;

import com.jiuqi.nr.customentry.define.CustomEntryContext;
import com.jiuqi.nr.customentry.define.CustomEntryData;
import com.jiuqi.nr.customentry.service.CustomeEntryServices;
import com.jiuqi.nr.definition.internal.BeanUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.customentry", "com.jiuqi.nr.customentry.service"})
@Configuration
public class CustomEntryConfig {
    public CustomeEntryServices getCustomeEntryServices() {
        return (CustomeEntryServices)BeanUtil.getBean(CustomeEntryServices.class);
    }

    @Bean
    @ConditionalOnMissingBean(value={CustomEntryContext.class})
    public CustomEntryContext getCustomEntryContext() {
        return new CustomEntryContext();
    }

    @Bean
    @ConditionalOnMissingBean(value={CustomEntryData.class})
    public CustomEntryData getCustomEntryData() {
        return new CustomEntryData();
    }
}

