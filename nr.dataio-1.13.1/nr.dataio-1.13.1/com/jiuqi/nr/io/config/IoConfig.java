/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.config;

import com.jiuqi.nr.io.bufdb.IOBufDBConfig;
import com.jiuqi.nr.io.config.NrIoProperties;
import com.jiuqi.nr.io.service.IDataTransferProvider;
import com.jiuqi.nr.io.service.impl.DefaultDataTransferProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.io"})
@EnableConfigurationProperties(value={NrIoProperties.class, IOBufDBConfig.class})
public class IoConfig
extends ApplicationObjectSupport {
    @Bean
    @ConditionalOnMissingBean(value={IDataTransferProvider.class})
    public IDataTransferProvider getIDataTransferProvider() {
        return new DefaultDataTransferProvider();
    }
}

