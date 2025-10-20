/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.gbi.framework.config.GBIConfigurationProperties
 */
package com.jiuqi.nvwa.gbi.config;

import com.jiuqi.nvwa.gbi.framework.config.GBIConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nvwa.gbi"})
@EnableConfigurationProperties(value={GBIConfigurationProperties.class})
@PropertySource(value={"classpath:/gbi-configuration.properties"})
public class NvwaGBIModuleConfig {
}

