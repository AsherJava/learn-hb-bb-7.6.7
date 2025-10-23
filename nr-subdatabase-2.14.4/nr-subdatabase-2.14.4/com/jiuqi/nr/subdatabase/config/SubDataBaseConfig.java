/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.config;

import com.jiuqi.nr.subdatabase.controller.Impl.SubDataBaseInfoProviderImpl;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.subdatabase"})
public class SubDataBaseConfig {
    @Bean
    @ConditionalOnMissingBean(value={SubDataBaseInfoProvider.class})
    public SubDataBaseInfoProvider getSubDataBaseInfoProviderImpl() {
        return new SubDataBaseInfoProviderImpl();
    }
}

