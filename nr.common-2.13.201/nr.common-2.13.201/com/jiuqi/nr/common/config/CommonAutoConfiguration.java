/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.config;

import com.jiuqi.nr.common.db.DataSourceConfig;
import com.jiuqi.nr.common.paramcheck.internal.CheckManagerService;
import com.jiuqi.nr.common.paramcheck.internal.impl.CheckManagerServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.common"})
@EnableConfigurationProperties(value={DataSourceConfig.class})
public class CommonAutoConfiguration {
    @Bean
    public CheckManagerService getCheckManagerService() {
        return new CheckManagerServiceImpl();
    }
}

