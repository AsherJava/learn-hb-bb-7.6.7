/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.np.definition.config;

import com.jiuqi.np.definition.controller.Impl.DefaultSubDatabaseTableNamesProviderImpl;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(value={"com.jiuqi.np.definition.common", "com.jiuqi.np.definition.controller", "com.jiuqi.np.definition.exception", "com.jiuqi.np.definition.facade", "com.jiuqi.np.definition.internal", "com.jiuqi.np.definition.observer", "com.jiuqi.np.definition.provider", "com.jiuqi.np.definition.progress"})
@EnableConfigurationProperties
@EnableTransactionManagement(proxyTargetClass=true)
public class DefinitionConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={SubDatabaseTableNamesProvider.class})
    public SubDatabaseTableNamesProvider getSubDatabaseTableNamesProvider() {
        return new DefaultSubDatabaseTableNamesProviderImpl();
    }
}

