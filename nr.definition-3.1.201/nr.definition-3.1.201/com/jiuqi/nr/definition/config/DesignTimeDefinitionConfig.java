/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.config;

import com.jiuqi.nr.definition.config.NrDefinitionCacheConfiguration;
import com.jiuqi.nr.definition.config.PrintCacheManagerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.definition"})
public class DesignTimeDefinitionConfig
extends ApplicationObjectSupport {
    @Value(value="${jiuqi.np.cache.type}")
    private String cacheType;

    @Bean
    public PrintCacheManagerConfiguration printCacheManagerConfiguration() {
        return new PrintCacheManagerConfiguration(this.cacheType);
    }

    @Bean
    public NrDefinitionCacheConfiguration nrDefinitionCacheConfiguration() {
        return new NrDefinitionCacheConfiguration();
    }
}

