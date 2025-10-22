/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.config;

import com.jiuqi.nr.configuration.controller.IBusinessConfigurationController;
import com.jiuqi.nr.configuration.internal.impl.BusinessConfigurationController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.configuration"})
@Configuration
public class ConfigurationConfig {
    @Bean
    @ConditionalOnMissingBean(value={IBusinessConfigurationController.class})
    public IBusinessConfigurationController getBusinessConfigurationController() {
        return new BusinessConfigurationController();
    }
}

