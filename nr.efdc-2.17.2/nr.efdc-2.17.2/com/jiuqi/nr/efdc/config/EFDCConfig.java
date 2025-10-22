/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.config;

import com.jiuqi.nr.efdc.dao.impl.EFDCServiceImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.efdc.service.IEFDCService;
import com.jiuqi.nr.efdc.service.impl.EFDCConfigServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.efdc"})
@Configuration
public class EFDCConfig {
    @Bean
    public IEFDCService IEFDCService() {
        return new EFDCServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IEFDCConfigService IEFDCConfigService() {
        return new EFDCConfigServiceImpl();
    }
}

