/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.config;

import com.jiuqi.nr.splittable.config.SplitGridCacheManagerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.splittable"})
public class SplitGrid2DataConfig {
    @Value(value="${jiuqi.np.cache.type}")
    private String cacheType;

    @Bean
    public SplitGridCacheManagerConfig splitGridCacheManagerConfig() {
        return new SplitGridCacheManagerConfig(this.cacheType);
    }
}

