/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.config;

import com.jiuqi.nr.integritycheck.errdescheck.IErrDesCheckService;
import com.jiuqi.nr.integritycheck.errdescheck.impl.DefaultErrDesCheckServiceImpl;
import com.jiuqi.nr.integritycheck.mapping.IErrDesMappingProvider;
import com.jiuqi.nr.integritycheck.mapping.impl.DefaultErrDesMappingProviderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.integritycheck"})
public class IntegritycheckConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={IErrDesCheckService.class})
    public IErrDesCheckService getIErrDesCheckService() {
        return new DefaultErrDesCheckServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(value={IErrDesMappingProvider.class})
    public IErrDesMappingProvider getIErrDesMappingProvider() {
        return new DefaultErrDesMappingProviderImpl();
    }
}

