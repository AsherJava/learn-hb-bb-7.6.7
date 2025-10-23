/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.config;

import com.jiuqi.nr.datascheme.config.AdjustCacheConfig;
import com.jiuqi.nr.datascheme.config.DataSchemeCacheConfig;
import com.jiuqi.nr.datascheme.config.DataSchemeCacheManagerConfiguration;
import com.jiuqi.nr.datascheme.config.DataSchemeExcel;
import com.jiuqi.nr.datascheme.config.DataSchemeFileConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"com.jiuqi.nr.datascheme.adjustment", "com.jiuqi.nr.datascheme.auth", "com.jiuqi.nr.datascheme.internal.service.impl", "com.jiuqi.nr.datascheme.loader", "com.jiuqi.nr.datascheme.internal.tree", "com.jiuqi.nr.datascheme.internal.dao", "com.jiuqi.nr.datascheme.internal.deploy", "com.jiuqi.nr.datascheme.internal.validator", "com.jiuqi.nr.datascheme.internal.job", "com.jiuqi.nr.datascheme.i18n", "com.jiuqi.nr.datascheme.sysopt", "com.jiuqi.nr.datascheme.web.rest"})
@Configuration
@EnableConfigurationProperties(value={DataSchemeExcel.class})
public class DataSchemeConfiguration {
    @Value(value="${jiuqi.np.cache.type:caffeine}")
    private String cacheType;

    @Bean
    public DataSchemeCacheManagerConfiguration dataSchemeCacheManagerConfiguration() {
        return new DataSchemeCacheManagerConfiguration();
    }

    @Bean
    public DataSchemeCacheConfig dataSchemeCacheConfig() {
        return new DataSchemeCacheConfig();
    }

    @Bean
    public DataSchemeFileConfig dataSchemeFileConfig() {
        return new DataSchemeFileConfig();
    }

    @Bean
    public AdjustCacheConfig adjustCacheConfig() {
        return new AdjustCacheConfig(this.cacheType);
    }
}

