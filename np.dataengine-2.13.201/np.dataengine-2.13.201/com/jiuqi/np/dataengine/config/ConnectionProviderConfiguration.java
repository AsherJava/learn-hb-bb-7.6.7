/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.config;

import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.impl.DefaultConnectionProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionProviderConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={IConnectionProvider.class})
    public IConnectionProvider getDefaultConnectionProvider() {
        return new DefaultConnectionProvider();
    }
}

