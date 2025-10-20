/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.config;

import com.jiuqi.va.datamodel.exchange.nvwa.pro.VaDataModelProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.datamodel.exchange.task"})
@EnableConfigurationProperties(value={VaDataModelProperties.class})
public class VaDataModelExchangeConfig {
}

