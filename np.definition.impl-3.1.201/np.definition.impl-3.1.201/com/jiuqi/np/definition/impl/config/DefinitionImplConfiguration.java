/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.impl.config;

import com.jiuqi.np.definition.impl.config.DefinitionImplCacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.np.definition.impl.controller", "com.jiuqi.np.definition.impl.internal.service", "com.jiuqi.np.definition.impl.proxy"})
public class DefinitionImplConfiguration {
    @Bean
    public DefinitionImplCacheConfig definitionImplCacheConfig() {
        return new DefinitionImplCacheConfig();
    }
}

